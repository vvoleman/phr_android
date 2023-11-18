package cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.SaveScheduleItemPresentationModel

class SaveScheduleItemPresentationModelToDomainMapper {

    fun toDomain(model: SaveScheduleItemPresentationModel): SaveScheduleItemDomainModel {
        return SaveScheduleItemDomainModel(
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

    fun toPresentation(model: SaveScheduleItemDomainModel): SaveScheduleItemPresentationModel {
        return SaveScheduleItemPresentationModel(
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
