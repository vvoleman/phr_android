package cz.vvoleman.phr.common.domain.facade

import cz.vvoleman.phr.common.utils.plusDayOfWeek
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

open class TranslateDateTimeFacade {

    private val alreadyTranslated = mutableMapOf<String, LocalDateTime>()

    fun translateScheduleItem(
        scheduleDayOfWeek: DayOfWeek,
        scheduleTime: LocalTime,
        currentWeekDay: DayOfWeek,
        currentDateTime: LocalDateTime,
        weekMultiplier: Int = 0
    ): LocalDateTime {
        val key = getStorageKey(currentDateTime, scheduleDayOfWeek, scheduleTime, weekMultiplier)
        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        if (!alreadyTranslated.containsKey(key)) {
            if (alreadyTranslated.size > getCacheLimit()) {
                alreadyTranslated.clear()
            }

            alreadyTranslated[key] =
                if (currentWeekDay === scheduleDayOfWeek && scheduleTime.isBefore(currentTime)) {
                    currentDate.plusDays(7L * (weekMultiplier + 1)).atTime(scheduleTime)
                } else {
                    currentDate.plusDayOfWeek(scheduleDayOfWeek).atTime(scheduleTime)
                        .plusDays(7L * weekMultiplier)
                }
        }

        return alreadyTranslated[key]!!
    }

    private fun getStorageKey(
        currentDateTime: LocalDateTime,
        week: DayOfWeek,
        time: LocalTime,
        weekMultiplier: Int = 0
    ): String {
        return "${currentDateTime.toEpochSecond(ZoneOffset.UTC)}${week.value}${time.toSecondOfDay()}$weekMultiplier"
    }

    protected open fun getCacheLimit(): Int {
        return DEFAULT_CACHE_LIMIT
    }

    companion object {
        private const val DEFAULT_CACHE_LIMIT = 100
    }
}
