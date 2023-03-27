package cz.vvoleman.phr.feature_medicalrecord.data.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.SaveFileRepository
import java.io.File
import java.io.FileOutputStream

class FileRepository(
    private val context: Context
) : SaveFileRepository {

    override fun saveFile(tempUri: Uri): String {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val extension = getExtension(tempUri)
        val file = File.createTempFile("MEDICAL_RECORD_", ".$extension", storageDir)

        val inputStream = context.contentResolver.openInputStream(tempUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        File(tempUri.path!!).delete()

        return file.absolutePath
    }

    private fun getExtension(uri: Uri): String {
        val resolver = context.contentResolver
        val mimeType = resolver.getType(uri)

        return mimeType ?: "jpeg"
    }
}