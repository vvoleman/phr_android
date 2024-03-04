package cz.vvoleman.phr.featureMeasurement.presentation.model.detail

import cz.vvoleman.phr.common.presentation.model.export.PermissionStatus
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

data class DetailMeasurementGroupViewState(
    val measurementGroup: MeasurementGroupPresentationModel,
    val fieldStats: List<FieldStatsPresentationModel> = emptyList(),
    val permissionStatus: PermissionStatus? = null,
)
