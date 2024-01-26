package cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

data class GetEntryByIdRequest(
    val entryId: String? = null,
    val measurementGroup: MeasurementGroupDomainModel,
)
