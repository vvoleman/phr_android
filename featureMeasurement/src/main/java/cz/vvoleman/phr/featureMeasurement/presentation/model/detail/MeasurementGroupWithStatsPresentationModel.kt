package cz.vvoleman.phr.featureMeasurement.presentation.model.detail

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

data class MeasurementGroupWithStatsPresentationModel(
    val measurementGroup: MeasurementGroupPresentationModel,
    val fieldStats: List<FieldStatsPresentationModel>,
)
