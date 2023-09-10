package cz.vvoleman.phr.featureMedicine.domain.usecase

import android.util.Range
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class GetScheduledInTimeRangeUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SchedulesInRangeRequestDomainModel, List<MedicineScheduleDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(
        request: SchedulesInRangeRequestDomainModel
    ): List<MedicineScheduleDomainModel> {
        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId)

        val startWeekDay = request.startAt?.dayOfWeek ?: DayOfWeek.MONDAY
        val endWeekDay = request.endAt?.dayOfWeek ?: DayOfWeek.SUNDAY
        val startTime = request.startAt?.toLocalTime() ?: LocalTime.MIN
        val endTime = request.endAt?.toLocalTime() ?: LocalTime.MAX

        val dayRange = Range.create(startWeekDay, endWeekDay)

        @Suppress("UnusedPrivateProperty")
        val timeRange = Range.create(startTime, endTime)

        val window = mutableListOf<MedicineScheduleDomainModel>()
        for (schedule in schedules) {
            val filteredByDate = filterByDateRange(request.startAt, request.endAt, schedule)
            val valid = getItemsDayMap(filteredByDate).filter {
                dayRange.contains(it.key)
            }.toList().map { it.second }.flatten()

            if (valid.isEmpty()) {
                continue
            }

            window.add(schedule)
        }

        return window
    }

    private fun filterByDateRange(
        startAt: LocalDateTime?,
        endAt: LocalDateTime?,
        schedule: MedicineScheduleDomainModel
    ): List<ScheduleItemDomainModel> {
        val valid = mutableListOf<ScheduleItemDomainModel>()

        for (item in schedule.schedules) {
            val schedulingRange = Range.create(item.scheduledAt, item.endingAt)
            val filterRange = Range.create(startAt, endAt)

            schedulingRange.intersect(filterRange) ?: continue

            valid.add(item)
        }

        return valid
    }

    private fun getItemsDayMap(items: List<ScheduleItemDomainModel>): Map<DayOfWeek, List<ScheduleItemDomainModel>> {
        val map = mutableMapOf<DayOfWeek, MutableList<ScheduleItemDomainModel>>()

        for (item in items) {
            if (!map.containsKey(item.dayOfWeek)) {
                map[item.dayOfWeek] = mutableListOf()
            }

            map[item.dayOfWeek]!!.add(item)
        }

        return map.mapValues { it.value.toList() }
    }
}
