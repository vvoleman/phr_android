package cz.vvoleman.phr.featureMedicine.domain.usecase.export

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.exception.InvalidDateRangeException
import cz.vvoleman.phr.featureMedicine.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.export.GetDataForExportRequest
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

class GetDataForExportUseCase(
    private val getSchedulesByMedicineRepository: GetSchedulesByMedicineRepository,
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetDataForExportRequest, List<ExportMedicineScheduleDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: GetDataForExportRequest): List<ExportMedicineScheduleDomainModel> {
        val dateRange = request.dateRange
        if (!validateDateRange(dateRange, request.currentDateTime)) {
            throw InvalidDateRangeException("Start date must be before end date")
        }

        // If medicine is empty, get all medicines
        val schedules = if (request.medicine.isEmpty()) {
            getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId)
        } else {
            getSchedulesByMedicineRepository.getSchedulesByMedicine(request.medicine.map { it.id }, request.patientId)
        }

        if (schedules.isEmpty()) {
            return emptyList()
        }

        val startAt = dateRange.first ?: schedules.minBy { it.createdAt }.createdAt
        val endAt = dateRange.second ?: request.currentDateTime

        // If dateRange is null, get all schedules
        val daysBetween = ChronoUnit.DAYS.between(startAt, endAt)
        print(request.patientId)
        var numberOfWeeks = (daysBetween / 7f)
        numberOfWeeks = if (numberOfWeeks < 1) {
            1f
        } else {
            ceil(numberOfWeeks) + 1
        }

        val translatedSchedules = TranslateDateTimeFacade
            .translate(schedules, startAt, numberOfWeeks.toInt())
            .filter {
                it.key.isAfter(startAt) && it.key.isBefore(endAt)
            }

        return mapResult(translatedSchedules.toMap())
    }

    private fun mapResult(schedules: Map<LocalDateTime, List<ScheduleItemWithDetailsDomainModel>>): List<ExportMedicineScheduleDomainModel> {
        val hashMap = mutableMapOf<String, LocalDateTime>()

        val flattened = schedules.flatMap { (key, value) ->
            value.sortedBy {
                it.medicine.name.uppercase()
            }.onEach {
                hashMap[it.scheduleItem.id!!] = key
            }
        }

        return flattened.groupBy { it.medicineScheduleId }.map { (id, list) ->
            ExportMedicineScheduleDomainModel(
                id = id,
                medicine = list.first().medicine,
                patient = list.first().patient,
                schedules = list.map {
                    ExportScheduleItemDomainModel(
                        id = it.scheduleItem.id!!,
                        dateTime = hashMap[it.scheduleItem.id]!!,
                        quantity = it.scheduleItem.quantity,
                        unit = it.scheduleItem.unit,
                    )
                }
            )
        }
    }

    private fun validateDateRange(
        dateRange: Pair<LocalDateTime?, LocalDateTime?>,
        currentDateTime: LocalDateTime
    ): Boolean {
        val (start, end) = dateRange
        return start?.isBefore(end) ?: true
    }
}