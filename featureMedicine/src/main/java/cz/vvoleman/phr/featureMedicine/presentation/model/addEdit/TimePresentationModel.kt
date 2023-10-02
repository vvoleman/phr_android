package cz.vvoleman.phr.featureMedicine.presentation.model.addEdit

import java.time.LocalTime

data class TimePresentationModel(
    val id: String?,
    val time: LocalTime,
    val number: Number
)
