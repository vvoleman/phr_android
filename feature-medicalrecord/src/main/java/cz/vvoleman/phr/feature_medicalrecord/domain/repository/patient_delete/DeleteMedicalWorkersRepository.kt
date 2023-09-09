package cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete

interface DeleteMedicalWorkersRepository {

    suspend fun deleteMedicalWorkers(patientId: String)
}
