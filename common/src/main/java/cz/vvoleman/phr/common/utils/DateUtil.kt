package cz.vvoleman.phr.common.utils

import android.icu.text.RelativeDateTimeFormatter
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
import kotlin.math.abs

fun LocalDateTime.toLocalString(): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return this.format(formatter)
}

fun LocalDateTime.toPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
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
    val fmt = RelativeDateTimeFormatter.getInstance(Locale.getDefault())
    val direction = if (this.isBefore(other)) {
        Direction.NEXT
    } else {
        Direction.LAST
    }

    if (this.year != other.year) {
        val value = abs(this.year - other.year).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.YEARS)
    }

    if (this.month != other.month) {
        val value = abs(this.month.value - other.month.value).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.MONTHS)
    }

    if (this.dayOfMonth != other.dayOfMonth) {
        val value = abs(this.dayOfMonth - other.dayOfMonth).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.DAYS)
    }

    return "dnes"
}

fun LocalDateTime.localizedDiff(other: LocalDateTime): String {
    // This methods returns a string that represents the difference between two dateTimes. It uses the RelativeDateTimeFormatter
    val fmt = RelativeDateTimeFormatter.getInstance(Locale.getDefault())
    val direction = if (this.isBefore(other)) {
        Direction.NEXT
    } else {
        Direction.LAST
    }

    // If diff year
    if (this.year != other.year) {
        val value = abs(this.year - other.year).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.YEARS)
    }

    if (this.month != other.month) {
        val value = abs(this.month.value - other.month.value).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.MONTHS)
    }

    if (this.dayOfMonth != other.dayOfMonth) {
        val value = abs(this.dayOfMonth - other.dayOfMonth).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.DAYS)
    }

    if (this.hour != other.hour) {
        val value = abs(this.hour - other.hour).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.HOURS)
    }

    if (this.minute != other.minute) {
        val value = abs(this.minute - other.minute).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.MINUTES)
    }

    if (this.second != other.second) {
        val value = abs(this.second - other.second).toDouble()
        return fmt.format(value, direction, RelativeDateTimeFormatter.RelativeUnit.SECONDS)
    }

    return "teď"
}

object DateTimePattern {
    const val DATE_TIME = "d. M. yyyy H:mm"
    const val DATE = "d. M. yyyy"
    const val TIME = "H:mm"
    const val TIME_SECONDS = "H:mm:ss"
}
