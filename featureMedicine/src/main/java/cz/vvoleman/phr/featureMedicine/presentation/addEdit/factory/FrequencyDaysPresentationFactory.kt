package cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory

import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.FrequencyDayPresentationModel
import java.time.DayOfWeek

class FrequencyDaysPresentationFactory {

    companion object {

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

}