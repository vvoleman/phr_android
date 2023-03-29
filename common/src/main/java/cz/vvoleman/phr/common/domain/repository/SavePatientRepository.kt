package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

interface SavePatientRepository {

    suspend fun savePatient(patient: PatientDomainModel): String

}