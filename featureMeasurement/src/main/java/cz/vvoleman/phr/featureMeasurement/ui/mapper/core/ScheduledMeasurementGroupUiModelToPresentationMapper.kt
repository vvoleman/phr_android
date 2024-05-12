package cz.vvoleman.phr.featureMeasurement.ui.mapper.core

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.ScheduledMeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel

class ScheduledMeasurementGroupUiModelToPresentationMapper(
    private val measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper
) {

    fun toPresentation(model: ScheduledMeasurementGroupUiModel): ScheduledMeasurementGroupPresentationModel {
        return ScheduledMeasurementGroupPresentationModel(
            measurementGroup = measurementGroupMapper.toPresentation(model.measurementGroup),
            dateTime = model.dateTime
        )
    }

    fun toPresentation(
        models: List<ScheduledMeasurementGroupUiModel>
    ): List<ScheduledMeasurementGroupPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: ScheduledMeasurementGroupPresentationModel): ScheduledMeasurementGroupUiModel {
        return ScheduledMeasurementGroupUiModel(
            measurementGroup = measurementGroupMapper.toUi(model.measurementGroup),
            dateTime = model.dateTime
        )
    }
}
