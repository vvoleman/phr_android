package cz.vvoleman.phr.featureMedicalRecord.domain.repository

interface DeleteMedicalRecord {

    suspend fun deleteMedicalRecord(recordId: String)
}
