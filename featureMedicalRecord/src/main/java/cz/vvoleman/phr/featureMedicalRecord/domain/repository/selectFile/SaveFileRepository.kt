package cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile

interface SaveFileRepository {

    fun saveFile(tempUri: String): String
}
