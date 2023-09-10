package cz.vvoleman.phr.featureMedicalRecord.data.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.SaveFileRepository
import java.io.File
import java.io.FileOutputStream

class FileRepository(
    private val context: Context
) : SaveFileRepository {

    override fun saveFile(tempUri: String): String {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val uri = Uri.parse(tempUri)
        val extension = getExtension(uri)
        val file = File.createTempFile("MEDICAL_RECORD_", ".$extension", storageDir)

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        File(tempUri).delete()

        Log.d("FileRepository", "File saved to ${file.absolutePath}")
        return file.absolutePath
    }

    private fun getExtension(uri: Uri): String {
        val resolver = context.contentResolver
        val mimeType = resolver.getType(uri)

        if (mimeType != null) {
            val split = mimeType.split("/")
            if (split.size > 1) {
                return split[1]
            }
        }

        return "jpeg"
    }
}
