package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface GetMeasurementGroupsByPatientRepository {
    suspend fun getMeasurementGroupsByPatient(patientId: String): List<MeasurementGroupDomainModel>
}
