package cz.vvoleman.phr.featureMedicine.domain.facade

import cz.vvoleman.phr.common.utils.plusDayOfWeek
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class TranslateDateTimeFacade {

    companion object {
        private val alreadyTranslated = mutableMapOf<String, LocalDateTime>()

        fun translate(
            schedules: List<MedicineScheduleDomainModel>,
            currentDateTime: LocalDateTime,
            numberOfWeeks: Int = 1
        ): Map<LocalDateTime, List<ScheduleItemWithDetailsDomainModel>> {
            val currentWeekDay = currentDateTime.dayOfWeek

            val translatedTimes = mutableMapOf<LocalDateTime, MutableList<ScheduleItemWithDetailsDomainModel>>()
            schedules.forEach { schedule ->
                schedule.schedules.forEach {
                    for (i in 0 until numberOfWeeks) {
                        val translated = translateScheduleItem(it, currentWeekDay, currentDateTime, i)

                        if (translated.isBefore(schedule.createdAt)) {
                            continue
                        }

                        val model = ScheduleItemWithDetailsDomainModel(
                            scheduleItem = it,
                            medicine = schedule.medicine,
                            patient = schedule.patient,
                            medicineScheduleId = schedule.id,
                            isAlarmEnabled = schedule.isAlarmEnabled
                        )

                        if (translatedTimes.containsKey(translated)) {
                            translatedTimes[translated]!!.add(model)
                        } else {
                            translatedTimes[translated] = mutableListOf(model)
                        }
                    }
                }
            }

            return translatedTimes.toSortedMap()
        }

        fun translateScheduleItem(scheduleItem: ScheduleItemDomainModel, dateTime: LocalDateTime): LocalDateTime {
            return translateScheduleItem(
                scheduleItem,
                dateTime.dayOfWeek,
                dateTime
            )
        }

        private fun translateScheduleItem(
            scheduleItem: ScheduleItemDomainModel,
            currentWeekDay: DayOfWeek,
            currentDateTime: LocalDateTime,
            weekMultiplier: Int = 0
        ): LocalDateTime {
            val key = getStorageKey(currentDateTime, scheduleItem.dayOfWeek, scheduleItem.time, weekMultiplier)
            val currentDate = currentDateTime.toLocalDate()
            val currentTime = currentDateTime.toLocalTime()

            if (!alreadyTranslated.containsKey(key)) {
                if (alreadyTranslated.size > 100) {
                    alreadyTranslated.clear()
                }

                alreadyTranslated[key] = if (currentWeekDay === scheduleItem.dayOfWeek && scheduleItem.time.isBefore(currentTime)) {
                    currentDate.plusDays(7L * (weekMultiplier + 1)).atTime(scheduleItem.time)
                } else {
                    currentDate.plusDayOfWeek(scheduleItem.dayOfWeek).atTime(scheduleItem.time)
                        .plusDays(7L * weekMultiplier)
                }
            }

            return alreadyTranslated[key]!!
        }

        private fun getStorageKey(currentDateTime: LocalDateTime, week: DayOfWeek, time: LocalTime, weekMultiplier: Int = 0): String {
            return "${currentDateTime.toEpochSecond(ZoneOffset.UTC)}${week.value}${time.toSecondOfDay()}$weekMultiplier"
        }
    }

}