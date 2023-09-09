package cz.vvoleman.phr.feature_medicalrecord.domain.repository

interface DeleteMedicalRecord {

    suspend fun deleteMedicalRecord(recordId: String)
}
