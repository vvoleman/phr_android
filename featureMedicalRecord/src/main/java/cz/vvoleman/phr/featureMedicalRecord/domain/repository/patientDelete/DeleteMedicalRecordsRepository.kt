package cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete

interface DeleteMedicalRecordsRepository {

    suspend fun deleteMedicalRecords(patientId: String)
}
