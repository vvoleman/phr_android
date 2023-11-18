package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import java.time.DayOfWeek

data class FrequencyDayPresentationModel(
    val day: DayOfWeek,
    val isSelected: Boolean = true,
)