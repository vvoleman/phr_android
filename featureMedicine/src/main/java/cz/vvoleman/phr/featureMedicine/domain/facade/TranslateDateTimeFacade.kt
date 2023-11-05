package cz.vvoleman.phr.featureMedicine.domain.facade

import cz.vvoleman.phr.common.utils.plusDayOfWeek
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TranslateDateTimeFacade {

    companion object {
        fun translate(
            schedules: List<MedicineScheduleDomainModel>,
            currentDateTime: LocalDateTime,
            numberOfWeeks: Int = 1
        ): Map<LocalDateTime, List<ScheduleItemWithDetailsDomainModel>> {
            val currentWeekDay = currentDateTime.dayOfWeek
            val currentTime = currentDateTime.toLocalTime()
            val currentDate = currentDateTime.toLocalDate()

            val translatedTimes = mutableMapOf<LocalDateTime, MutableList<ScheduleItemWithDetailsDomainModel>>()
            schedules.forEach { schedule ->
                schedule.schedules.forEach {
                    for (i in 0 until numberOfWeeks) {
                        val translated = translateScheduleItem(it, currentWeekDay, currentTime, currentDate, i)

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
                dateTime.toLocalTime(),
                dateTime.toLocalDate()
            )
        }

        private fun translateScheduleItem(
            scheduleItem: ScheduleItemDomainModel,
            currentWeekDay: DayOfWeek,
            currentTime: LocalTime,
            currentDate: LocalDate,
            weekMultiplier: Int = 0
        ): LocalDateTime {
            return if (currentWeekDay === scheduleItem.dayOfWeek && scheduleItem.time.isBefore(currentTime)) {
                currentDate.plusDays(7L * (weekMultiplier + 1)).atTime(scheduleItem.time)
            } else {
                currentDate.plusDayOfWeek(scheduleItem.dayOfWeek).atTime(scheduleItem.time)
                    .plusDays(7L * weekMultiplier)
            }
        }
    }

}