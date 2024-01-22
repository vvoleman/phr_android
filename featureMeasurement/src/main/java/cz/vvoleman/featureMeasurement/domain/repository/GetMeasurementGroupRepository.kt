package cz.vvoleman.featureMeasurement.domain.repository

import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface GetMeasurementGroupRepository {

    suspend fun getMeasurementGroup(id: String): MeasurementGroupDomainModel?

}
