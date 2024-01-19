package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupEntryUiModel

class MeasurementGroupEntryUiModelToPresentationMapper {

    fun toPresentation(model: MeasurementGroupEntryUiModel): MeasurementGroupEntryPresentationModel {
        return MeasurementGroupEntryPresentationModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId,
        )
    }

    fun toUi(model: MeasurementGroupEntryPresentationModel): MeasurementGroupEntryUiModel {
        return MeasurementGroupEntryUiModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId,
        )
    }

}
