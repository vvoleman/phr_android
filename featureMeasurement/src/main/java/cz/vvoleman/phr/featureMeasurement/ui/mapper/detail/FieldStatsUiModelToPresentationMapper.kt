package cz.vvoleman.phr.featureMeasurement.ui.mapper.detail

import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.FieldStatsPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.FieldStatsUiModel

class FieldStatsUiModelToPresentationMapper {

    fun toPresentation(model: FieldStatsUiModel): FieldStatsUiModel {
        return FieldStatsUiModel(
            fieldId = model.fieldId,
            name = model.name,
            unit = model.unit,
            values = model.values,
            minValue = model.minValue,
            maxValue = model.maxValue,
            weekAvgValue = model.weekAvgValue,
        )
    }

    fun toUi(model: FieldStatsPresentationModel): FieldStatsUiModel {
        return FieldStatsUiModel(
            fieldId = model.fieldId,
            name = model.name,
            unit = model.unit,
            values = model.values,
            minValue = model.minValue,
            maxValue = model.maxValue,
            weekAvgValue = model.weekAvgValue,
        )
    }

}
