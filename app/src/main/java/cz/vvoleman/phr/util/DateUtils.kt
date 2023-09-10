package cz.vvoleman.phr.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

// Add method getDayOfMonth to every date instance

fun LocalDate.getCurrentDay(): Int {
//    val cal = this.getCalendar()
//    return cal.get(Calendar.DAY_OF_MONTH)
    return this.dayOfMonth
}

fun LocalDate.getCurrentMonth(): Int {
//    val cal = this.getCalendar()
//    return cal.get(Calendar.MONTH) + 1
    return this.monthValue
}

fun LocalDate.getCurrentYear(): Int {
//    val cal = this.getCalendar()
//    return cal.get(Calendar.YEAR)
    return this.year
}

fun LocalDate.getNameOfDay(short: Boolean = false): String {
    val version = if (short) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE
    return this.dayOfWeek.getDisplayName(version, Locale.getDefault())
}

fun LocalDate.getNameOfMonth(short: Boolean = false): String {
    val pattern = if (short) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE
    return this.month.getDisplayName(pattern, Locale.getDefault())
}

fun LocalDate.getByPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

fun Date.getCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    return calendar
}
