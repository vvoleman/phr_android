package cz.vvoleman.phr.featureMedicine.data.mapper.schedule

import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.save.SaveScheduleItemDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel

class SaveScheduleItemDomainModelToDataMapper {

    fun toData(model: SaveScheduleItemDomainModel): SaveScheduleItemDataModel {
        return SaveScheduleItemDataModel(
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            unit = model.unit,
            description = model.description
        )
    }
}
