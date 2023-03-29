package cz.vvoleman.phr.common.utils

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

fun LocalDate.getNameOfDay(short: Boolean = false): String {
    return this.dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
}

fun LocalDate.getNameOfMonth(short: Boolean = false): String {
    val pattern = if (short) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE
    return this.month.getDisplayName(pattern, Locale.getDefault())
}