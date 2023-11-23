package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

interface GetMedicalWorkersForPatientRepository {

    suspend fun getMedicalWorkersForPatient(patientId: String): List<MedicalWorkerDomainModel>
}
