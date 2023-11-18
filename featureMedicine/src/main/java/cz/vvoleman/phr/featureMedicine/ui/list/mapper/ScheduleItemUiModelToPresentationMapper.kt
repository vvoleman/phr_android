package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemUiModel

class ScheduleItemUiModelToPresentationMapper {

    fun toPresentation(model: ScheduleItemUiModel): ScheduleItemPresentationModel {
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

    fun toUi(model: ScheduleItemPresentationModel): ScheduleItemUiModel {
        return ScheduleItemUiModel(
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
