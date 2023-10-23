package cz.vvoleman.phr.common.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

fun LocalDate.getNameOfDay(short: Boolean = false): String {
    val version = if (short) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE
    return this.dayOfWeek.getDisplayName(version, Locale.getDefault())
}

fun LocalDate.getNameOfMonth(short: Boolean = false): String {
    val pattern = if (short) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE
    return this.month.getDisplayName(pattern, Locale.getDefault())
}

fun LocalTime.toEpochSeconds(): Long {
    return this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toEpochSecond()
}
