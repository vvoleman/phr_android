package cz.vvoleman.phr.common.presentation.factory

import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import java.time.DayOfWeek

object FrequencyDaysPresentationFactory {

    fun makeDays(): List<FrequencyDayPresentationModel> {
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

    fun makeDaysAsMap(): Map<DayOfWeek, FrequencyDayPresentationModel> {
        return makeDays().associateBy { it.day }
    }
}
