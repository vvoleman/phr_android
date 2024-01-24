package cz.vvoleman.phr.featureMeasurement.domain.model.list

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

data class DeleteMeasurementGroupRequest(
    val measurementGroup: MeasurementGroupDomainModel,
)
