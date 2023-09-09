package cz.vvoleman.phr.feature_medicine.data.mapper.schedule

import cz.vvoleman.phr.feature_medicine.data.model.schedule.ScheduleItemDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.save.SaveScheduleItemDomainModel

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
