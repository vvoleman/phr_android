package cz.vvoleman.phr.featureMeasurement.ui.model.detail

import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel

data class MeasurementGroupWithStatsUiModel(
    val measurementGroup: MeasurementGroupUiModel,
    val fieldStats: List<FieldStatsUiModel>,
)
