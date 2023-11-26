package cz.vvoleman.phr.common.domain.repository.patient

import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel

interface SavePatientRepository {

    suspend fun savePatient(patient: AddEditPatientDomainModel): String
}
