package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel

interface SavePatientRepository {

    suspend fun savePatient(patient: AddEditPatientDomainModel): String
}
