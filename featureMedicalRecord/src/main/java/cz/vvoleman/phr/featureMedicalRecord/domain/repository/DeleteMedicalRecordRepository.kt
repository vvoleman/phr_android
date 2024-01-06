package cz.vvoleman.phr.featureMedicalRecord.domain.repository

interface DeleteMedicalRecordRepository {

    suspend fun deleteMedicalRecord(recordId: String)
}
