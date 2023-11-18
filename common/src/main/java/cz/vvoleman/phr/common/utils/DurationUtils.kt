package cz.vvoleman.phr.common.utils

import java.time.Duration

@Suppress("MagicNumber")
fun Duration.getMinutesPart(): Int {
    val minutes = this.toMinutes().toInt()
    return minutes % 60
}

@Suppress("MagicNumber")
fun Duration.getHoursPart(): Int {
    val hours = this.toHours().toInt()
    return hours % 24
}

@Suppress("MagicNumber")
fun Duration.getSecondsPart(): Int {
    val seconds = this.seconds.toInt()
    return seconds % 60
}
