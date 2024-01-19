package cz.vvoleman.phr.common.ui.component.frequencySelector

import java.time.DayOfWeek

data class FrequencyDayUiModel(
    val day: DayOfWeek,
    val isSelected: Boolean = true,
)
