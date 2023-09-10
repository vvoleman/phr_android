package cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete

interface DeleteMedicalWorkersRepository {

    suspend fun deleteMedicalWorkers(patientId: String)
}
