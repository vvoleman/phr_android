package cz.vvoleman.phr.featureMedicine.ui.frequencySelector

import java.time.DayOfWeek

data class FrequencyDayUiModel(
    val day: DayOfWeek,
    val isSelected: Boolean = true,
) {
    companion object {
        fun makeWeek(): List<FrequencyDayUiModel> {
            return listOf(
                FrequencyDayUiModel(DayOfWeek.MONDAY),
                FrequencyDayUiModel(DayOfWeek.TUESDAY),
                FrequencyDayUiModel(DayOfWeek.WEDNESDAY),
                FrequencyDayUiModel(DayOfWeek.THURSDAY),
                FrequencyDayUiModel(DayOfWeek.FRIDAY),
                FrequencyDayUiModel(DayOfWeek.SATURDAY),
                FrequencyDayUiModel(DayOfWeek.SUNDAY),
            )
        }
    }
}