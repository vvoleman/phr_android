package cz.vvoleman.phr.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

// Add method getDayOfMonth to every date instance

fun Date.getCurrentDay(): Int {
    val cal = this.getCalendar()
    return cal.get(Calendar.DAY_OF_MONTH)
}

fun Date.getCurrentMonth(): Int {
    val cal = this.getCalendar()
    return cal.get(Calendar.MONTH) + 1
}

fun Date.getCurrentYear(): Int {
    val cal = this.getCalendar()
    return cal.get(Calendar.YEAR)
}

fun Date.getNameOfDay(short: Boolean = false): String {
    val pattern = if (short) Calendar.SHORT else Calendar.LONG
    return this.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, pattern, Locale.getDefault()) ?: ""
}

fun Date.getNameOfMonth(short: Boolean = false): String {
    val pattern = if (short) Calendar.SHORT else Calendar.LONG_STANDALONE
    return this.getCalendar().getDisplayName(Calendar.MONTH, pattern, Locale.getDefault()) ?: ""
}

fun Date.getByPattern(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

fun Date.getCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    return calendar
}