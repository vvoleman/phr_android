package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface ScheduleMeasurementGroupRepository {

    suspend fun scheduleMeasurementGroup(model: MeasurementGroupDomainModel): Boolean
}
