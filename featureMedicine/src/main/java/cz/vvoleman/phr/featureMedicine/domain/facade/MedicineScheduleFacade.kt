package cz.vvoleman.phr.featureMedicine.domain.facade

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.LocalDateTime

class MedicineScheduleFacade {

    companion object {

        fun getNextScheduleItem(
            scheduleItems: List<ScheduleItemWithDetailsDomainModel>,
            currentDateTime: LocalDateTime
        ): List<ScheduleItemWithDetailsDomainModel> {

            val translated = scheduleItems.groupBy {
                it.scheduleItem.getTranslatedDateTime(currentDateTime)
            }.toSortedMap()

            // Return first item
            return translated.values.firstOrNull() ?: emptyList()
        }

    }
}