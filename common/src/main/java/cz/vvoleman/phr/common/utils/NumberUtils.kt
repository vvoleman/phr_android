package cz.vvoleman.phr.common.utils

fun Int.withLeadingZero(n: Int = 2): String {
    return String.format("%0${n}d", this)
}