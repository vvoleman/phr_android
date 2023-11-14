package cz.vvoleman.phr.featureMedicine.ui.component.frequencySelector

import java.time.DayOfWeek

data class FrequencyDayUiModel(
    val day: DayOfWeek,
    val isSelected: Boolean = true,
) {
}