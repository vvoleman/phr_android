package cz.vvoleman.phr.featureMeasurement.domain.facade

import cz.vvoleman.phr.common.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupScheduleItemDomainModel
import java.time.LocalDateTime

class MeasurementTranslateDateTimeFacade : TranslateDateTimeFacade() {

    fun translate(
        schedules: List<MeasurementGroupDomainModel>,
        currentDateTime: LocalDateTime,
        numberOfWeeks: Int = 1
    ): Map<LocalDateTime, List<MeasurementGroupDomainModel>> {
        val currentWeekDay = currentDateTime.dayOfWeek

        val translatedTimes = mutableMapOf<LocalDateTime, MutableList<MeasurementGroupDomainModel>>()
        schedules.forEach {schedule ->
            schedule.scheduleItems.forEach {
                for (i in 0 until numberOfWeeks) {
                    val translated = translateScheduleItem(
                        scheduleDayOfWeek = it.dayOfWeek,
                        scheduleTime = it.time,
                        currentWeekDay = currentWeekDay,
                        currentDateTime = currentDateTime,
                        weekMultiplier = i
                    )

                    if (translated.isBefore(it.scheduledAt)) {
                        continue
                    }

                    if (translatedTimes.containsKey(translated)) {
                        translatedTimes[translated]!!.add(schedule)
                    } else {
                        translatedTimes[translated] = mutableListOf(schedule)
                    }
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

}
