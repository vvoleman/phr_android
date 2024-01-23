package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextScheduleItemPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class ScheduleItemWithDetailsDomainModelToNextScheduleMapper {

    fun toNextSchedule(models: List<ScheduleItemWithDetailsDomainModel>): NextSchedulePresentationModel {
        val time = models.first()
            .scheduleItem
            .getTranslatedDateTime(LocalDateTime.now())

        return NextSchedulePresentationModel(
            id = time.toEpochSecond(ZoneOffset.UTC).toString(),
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
