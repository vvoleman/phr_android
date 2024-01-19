package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupScheduleItemPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupScheduleItemUiModel

class MeasurementGroupScheduleItemUiModelToPresentationMapper {
    fun toPresentation(model: MeasurementGroupScheduleItemUiModel): MeasurementGroupScheduleItemPresentationModel {
        return MeasurementGroupScheduleItemPresentationModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toUi(model: MeasurementGroupScheduleItemPresentationModel): MeasurementGroupScheduleItemUiModel {
        return MeasurementGroupScheduleItemUiModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

}
