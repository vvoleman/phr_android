package cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete

interface DeleteMedicalRecordsRepository {

    suspend fun deleteMedicalRecords(patientId: String)

}