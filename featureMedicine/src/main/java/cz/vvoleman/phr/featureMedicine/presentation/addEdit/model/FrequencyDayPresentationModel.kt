package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import java.time.DayOfWeek

data class FrequencyDayPresentationModel (
    val day: DayOfWeek,
    val isSelected: Boolean = true,
) {
    companion object {
        fun makeWeek(): List<FrequencyDayPresentationModel> {
            return listOf(
                FrequencyDayPresentationModel(DayOfWeek.MONDAY),
                FrequencyDayPresentationModel(DayOfWeek.TUESDAY),
                FrequencyDayPresentationModel(DayOfWeek.WEDNESDAY),
                FrequencyDayPresentationModel(DayOfWeek.THURSDAY),
                FrequencyDayPresentationModel(DayOfWeek.FRIDAY),
                FrequencyDayPresentationModel(DayOfWeek.SATURDAY),
                FrequencyDayPresentationModel(DayOfWeek.SUNDAY),
            )
        }
    }
}