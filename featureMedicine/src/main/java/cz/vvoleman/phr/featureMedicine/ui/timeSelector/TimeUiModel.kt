package cz.vvoleman.phr.featureMedicine.ui.timeSelector

import java.time.LocalTime

data class TimeUiModel(
    val id: String? = null,
    val time: LocalTime,
    val number: Number
)
