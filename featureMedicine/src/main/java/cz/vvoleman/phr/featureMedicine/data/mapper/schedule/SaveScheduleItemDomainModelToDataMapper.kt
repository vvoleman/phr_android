package cz.vvoleman.phr.featureMedicine.data.mapper.schedule

import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel

class SaveScheduleItemDomainModelToDataMapper {

    fun toData(model: SaveScheduleItemDomainModel): ScheduleItemDataModel {
        return ScheduleItemDataModel(
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description
        )
    }
}
