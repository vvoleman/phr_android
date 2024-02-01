package cz.vvoleman.phr.featureMeasurement.domain.model.detail

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

data class GetFieldStatsRequest(
    val measurementGroup: MeasurementGroupDomainModel,
)
