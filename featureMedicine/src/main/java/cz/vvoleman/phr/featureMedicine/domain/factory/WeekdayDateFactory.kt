package cz.vvoleman.phr.featureMedicine.domain.factory

import java.time.DayOfWeek
import java.time.LocalDate

class WeekdayDateFactory {

    companion object {
        fun getNextWeekdayDate(next: DayOfWeek, currentDate: LocalDate): LocalDate {
            val current = currentDate.dayOfWeek

            if (current == next) {
                return currentDate
            }

            val daysToAdd = if (current.value > next.value) {
                7 - current.value + next.value
            } else {
                next.value - current.value
            }

            return currentDate.plusDays(daysToAdd.toLong())
        }
    }

}