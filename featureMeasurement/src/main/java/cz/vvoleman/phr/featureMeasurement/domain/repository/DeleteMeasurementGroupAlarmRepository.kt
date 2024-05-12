package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface DeleteMeasurementGroupAlarmRepository {

    suspend fun deleteMeasurementGroupAlarm(model: MeasurementGroupDomainModel): Boolean
}
