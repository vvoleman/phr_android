package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemPresentationModel

class ScheduleItemPresentationModelToDomainMapper {

    fun toDomain(model: ScheduleItemPresentationModel): ScheduleItemDomainModel {
        return ScheduleItemDomainModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            unit = model.unit,
            description = model.description
        )
    }

    fun toPresentation(model: ScheduleItemDomainModel): ScheduleItemPresentationModel {
        return ScheduleItemPresentationModel(
            id = model.id,
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