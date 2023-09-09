package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel

interface GetMedicalWorkersForPatientRepository {

    suspend fun getMedicalWorkersForPatient(patientId: String): List<MedicalWorkerDomainModel>
}
