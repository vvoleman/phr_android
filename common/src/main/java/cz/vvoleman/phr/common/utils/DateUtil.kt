package cz.vvoleman.phr.common.utils

import android.icu.text.RelativeDateTimeFormatter
import android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit
import android.icu.text.RelativeDateTimeFormatter.Direction
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

fun LocalDateTime.toLocalString(): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime? {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return LocalDateTime.parse(this, formatter)
}

fun LocalDate.toLocalString(): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return this.format(formatter)
}

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

@Suppress("MagicNumber")
fun LocalDate.plusDayOfWeek(next: DayOfWeek): LocalDate {
    val current = this.dayOfWeek

    if (current == next) {
        return this
    }

    val daysToAdd = if (current.value > next.value) {
        7 - current.value + next.value
    } else {
        next.value - current.value
    }

    return this.plusDays(daysToAdd.toLong())
}

fun DayOfWeek.getLocalString(textStyle: TextStyle = TextStyle.FULL_STANDALONE): String {
    return this.getDisplayName(textStyle, Locale.getDefault())
}

fun LocalDate.localizedDiff(other: LocalDate): String {
    val days = this.toEpochDay() - other.toEpochDay()
    val fmt = RelativeDateTimeFormatter.getInstance(Locale.getDefault())

    return when {
        days < 31 -> fmt.format(Direction.LAST, AbsoluteUnit.DAY)
        days < 365 -> fmt.format(Direction.LAST, AbsoluteUnit.MONTH)
        else -> fmt.format(Direction.LAST, AbsoluteUnit.YEAR)
    }
}
