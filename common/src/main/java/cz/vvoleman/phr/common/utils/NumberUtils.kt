package cz.vvoleman.phr.common.utils

import java.util.Locale

fun Int.withLeadingZero(n: Int = 2): String {
    val locale = Locale.getDefault()
    return String.format(locale, "%0${n}d", this)
}
