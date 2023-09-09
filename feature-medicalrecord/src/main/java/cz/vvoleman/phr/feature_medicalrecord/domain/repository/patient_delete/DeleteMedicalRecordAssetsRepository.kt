package cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete

interface DeleteMedicalRecordAssetsRepository {

    suspend fun deleteMedicalRecordAssets(patientId: String)
}
