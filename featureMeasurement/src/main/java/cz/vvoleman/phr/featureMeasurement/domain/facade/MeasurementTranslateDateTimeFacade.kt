package cz.vvoleman.phr.featureMeasurement.domain.facade

import cz.vvoleman.phr.common.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupScheduleItemDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.ScheduledMeasurementGroupDomainModel
import java.time.LocalDateTime

class MeasurementTranslateDateTimeFacade : TranslateDateTimeFacade() {

    fun translate(
        schedules: List<MeasurementGroupDomainModel>,
        currentDateTime: LocalDateTime,
        numberOfWeeks: Int = 1
    ): Map<LocalDateTime, List<ScheduledMeasurementGroupDomainModel>> {
        val currentWeekDay = currentDateTime.dayOfWeek

        val translatedTimes = mutableMapOf<LocalDateTime, MutableList<ScheduledMeasurementGroupDomainModel>>()

        val pairs = createPairs(schedules)

        pairs.forEach { (schedule, item) ->
            for (i in 0 until numberOfWeeks) {
                val translated = translateScheduleItem(
                    scheduleDayOfWeek = item.dayOfWeek,
                    scheduleTime = item.time,
                    currentWeekDay = currentWeekDay,
                    currentDateTime = currentDateTime,
                    weekMultiplier = i
                )

                if (translated.isBefore(item.scheduledAt)) {
                    continue
                }

                val scheduledGroup = ScheduledMeasurementGroupDomainModel(
                    measurementGroup = schedule,
                    dateTime = translated
                )

                if (translatedTimes.containsKey(translated)) {
                    translatedTimes[translated]!!.add(scheduledGroup)
                } else {
                    translatedTimes[translated] = mutableListOf(scheduledGroup)
                }
            }
        }

        return translatedTimes.toSortedMap()
    }

    fun getLocalTime(
        item: MeasurementGroupScheduleItemDomainModel,
        currentDateTime: LocalDateTime
    ): LocalDateTime {
        return translateScheduleItem(
            scheduleDayOfWeek = item.dayOfWeek,
            scheduleTime = item.time,
            currentWeekDay = currentDateTime.dayOfWeek,
            currentDateTime = currentDateTime,
            weekMultiplier = 0
        )
    }

    private fun createPairs(
        schedules: List<MeasurementGroupDomainModel>
    ): List<Pair<MeasurementGroupDomainModel, MeasurementGroupScheduleItemDomainModel>> {
        return schedules.flatMap { schedule ->
            schedule.scheduleItems.map { item ->
                Pair(schedule, item)
            }
        }
    }

}
