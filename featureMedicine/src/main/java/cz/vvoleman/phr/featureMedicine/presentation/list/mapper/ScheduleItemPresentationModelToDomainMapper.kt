package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemPresentationModel

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