package cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete

interface DeleteMedicalRecordAssetsRepository {

    suspend fun deleteMedicalRecordAssets(patientId: String)
}
