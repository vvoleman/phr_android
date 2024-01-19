package cz.vvoleman.phr.common.presentation.model.frequencySelector

import java.time.DayOfWeek

data class FrequencyDayPresentationModel(
    val day: DayOfWeek,
    val isSelected: Boolean = true,
)
