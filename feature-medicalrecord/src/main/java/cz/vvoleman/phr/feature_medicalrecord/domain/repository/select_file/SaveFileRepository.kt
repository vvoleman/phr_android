package cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file

interface SaveFileRepository {

    fun saveFile(tempUri: String): String

}