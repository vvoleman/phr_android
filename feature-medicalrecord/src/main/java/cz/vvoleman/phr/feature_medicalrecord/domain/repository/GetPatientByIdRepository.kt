package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel

interface GetPatientByIdRepository {

    suspend fun getPatientById(id: String): PatientDomainModel?

}