package cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit

interface DeleteUnusedFilesRepository {

    suspend fun deleteUnusedFiles(medicalRecordId: String, files: List<String>)

}
