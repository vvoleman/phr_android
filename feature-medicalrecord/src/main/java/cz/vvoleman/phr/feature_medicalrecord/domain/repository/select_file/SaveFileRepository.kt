package cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file

import android.net.Uri

interface SaveFileRepository {

    fun saveFile(tempUri: Uri): String

}