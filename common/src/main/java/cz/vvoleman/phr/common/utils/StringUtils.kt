package cz.vvoleman.phr.common.utils

fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}
