package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface GetMeasurementGroupRepository {

    suspend fun getMeasurementGroup(id: String): MeasurementGroupDomainModel?

}
