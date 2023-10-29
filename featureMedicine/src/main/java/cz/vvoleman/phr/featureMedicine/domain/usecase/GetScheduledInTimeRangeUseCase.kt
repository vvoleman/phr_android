package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.factory.TranslateDateTimeFactory
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

class GetScheduledInTimeRangeUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SchedulesInRangeRequestDomainModel, List<MedicineScheduleDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(
        request: SchedulesInRangeRequestDomainModel
    ): List<MedicineScheduleDomainModel> {
        if (request.startAt.isAfter(request.endAt)) {
            throw IllegalArgumentException("StartAt limit must be before endAt limit")
        }

        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId)
        val daysBetween = ChronoUnit.DAYS.between(request.startAt.toLocalDate(), request.endAt.toLocalDate())
        var numberOfWeeks = (daysBetween / 7f)
        numberOfWeeks = if (numberOfWeeks < 1) {
            1f
        } else {
            ceil(numberOfWeeks) + 1
        }

        val translatedSchedules = TranslateDateTimeFactory.translate(schedules, request.startAt, numberOfWeeks.toInt())

        val results = mutableListOf<MedicineScheduleDomainModel>()

        for ((key, value) in translatedSchedules) {
            if (key.isBefore(request.startAt) || key.isAfter(request.endAt)) {
                continue
            }

            results.addAll(value)
        }

        return results
    }
}
