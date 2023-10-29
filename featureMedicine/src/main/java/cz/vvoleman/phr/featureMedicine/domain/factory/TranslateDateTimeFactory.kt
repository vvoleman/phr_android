package cz.vvoleman.phr.featureMedicine.domain.factory

import cz.vvoleman.phr.common.utils.plusDayOfWeek
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import java.time.LocalDateTime

class TranslateDateTimeFactory {

    companion object {
        fun translate(schedules: List<MedicineScheduleDomainModel>, currentDateTime: LocalDateTime, numberOfWeeks: Int = 1): Map<LocalDateTime, List<MedicineScheduleDomainModel>> {
            val currentWeekDay = currentDateTime.dayOfWeek
            val currentTime = currentDateTime.toLocalTime()
            val currentDate = currentDateTime.toLocalDate()

            val translatedTimes = mutableMapOf<LocalDateTime, MutableList<MedicineScheduleDomainModel>>()
            schedules.forEach {schedule ->
                schedule.schedules.forEach {
                    for (i in 0 until numberOfWeeks) {
                        val translated = if (currentWeekDay === it.dayOfWeek && it.time.isBefore(currentTime)) {
                            currentDate.plusDays(7L * i + 1).atTime(it.time)
                        } else {
                            currentDate.plusDayOfWeek(it.dayOfWeek).atTime(it.time).plusDays(7L * i)
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
    }

}