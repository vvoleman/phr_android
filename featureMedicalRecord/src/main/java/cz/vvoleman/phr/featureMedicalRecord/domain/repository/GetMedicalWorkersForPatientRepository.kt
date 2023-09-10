package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalWorkerDomainModel

interface GetMedicalWorkersForPatientRepository {

    suspend fun getMedicalWorkersForPatient(patientId: String): List<MedicalWorkerDomainModel>
}
