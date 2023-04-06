package cz.vvoleman.phr.feature_medicine.data.mapper.schedule

import cz.vvoleman.phr.feature_medicine.data.model.schedule.ScheduleItemDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.ScheduleItemDomainModel

class ScheduleItemDataModelToDomainMapper {

    fun toDomain(model: ScheduleItemDataModel): ScheduleItemDomainModel {
        return ScheduleItemDomainModel(
            id = model.id!!,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description
        )
    }

    fun toData(model: ScheduleItemDomainModel): ScheduleItemDataModel {
        return ScheduleItemDataModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description
        )
    }

}