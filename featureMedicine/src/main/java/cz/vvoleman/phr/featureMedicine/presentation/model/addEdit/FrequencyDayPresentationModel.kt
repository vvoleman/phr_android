package cz.vvoleman.phr.featureMedicine.presentation.model.addEdit

import java.time.DayOfWeek

data class FrequencyDayPresentationModel (
    val day: DayOfWeek,
    val isSelected: Boolean = true,
)