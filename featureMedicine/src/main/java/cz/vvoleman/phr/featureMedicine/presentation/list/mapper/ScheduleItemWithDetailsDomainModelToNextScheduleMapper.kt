package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextScheduleItemPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.LocalDateTime

class ScheduleItemWithDetailsDomainModelToNextScheduleMapper {

    fun toNextSchedule(models: List<ScheduleItemWithDetailsDomainModel>): NextSchedulePresentationModel {
        require(models.isNotEmpty()) { "List of ScheduleItemWithDetailsDomainModel must not be empty" }

        val time = models.first()
            .scheduleItem
            .getTranslatedDateTime(LocalDateTime.now())

        return NextSchedulePresentationModel(
            dateTime = time,
            items = models.map {
                NextScheduleItemPresentationModel(
                    id = it.scheduleItem.id!!,
                    time = time,
                    name = it.medicine.name,
                    additionalInfo = "${it.scheduleItem.quantity} ${it.scheduleItem.unit}"
                )
            }
        )
    }
}
