package cz.vvoleman.phr.featureMeasurement.ui.mapper.detail

import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.MeasurementGroupWithStatsPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.MeasurementGroupWithStatsUiModel

class MeasurementGroupWithStatsUiModelToPresentationMapper(
    private val fieldStatsMapper: FieldStatsUiModelToPresentationMapper,
    private val measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper
) {

    fun toPresentation(model: MeasurementGroupWithStatsUiModel): MeasurementGroupWithStatsPresentationModel {
        return MeasurementGroupWithStatsPresentationModel(
            measurementGroup = measurementGroupMapper.toPresentation(model.measurementGroup),
            fieldStats = model.fieldStats.map { fieldStatsMapper.toPresentation(it) }
        )
    }

    fun toPresentation(models: List<MeasurementGroupWithStatsUiModel>): List<MeasurementGroupWithStatsPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: MeasurementGroupWithStatsPresentationModel): MeasurementGroupWithStatsUiModel {
        return MeasurementGroupWithStatsUiModel(
            measurementGroup = measurementGroupMapper.toUi(model.measurementGroup),
            fieldStats = model.fieldStats.map { fieldStatsMapper.toUi(it) }
        )
    }

    fun toUi(models: List<MeasurementGroupWithStatsPresentationModel>): List<MeasurementGroupWithStatsUiModel> {
        return models.map { toUi(it) }
    }
}
