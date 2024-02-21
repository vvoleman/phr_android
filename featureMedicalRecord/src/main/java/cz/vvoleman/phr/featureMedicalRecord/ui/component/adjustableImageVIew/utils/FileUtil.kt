package cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.utils

import androidx.activity.ComponentActivity
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

/**
 * This class contains a helper function creating temporary files
 *
 * @constructor creates file util
 */
class FileUtil {
    /**
     * create a temporary file
     *
     * @param activity the current activity
     * @param pageNumber the current document page number
     */
    @Throws(IOException::class)
    fun createImageFile(activity: ComponentActivity, pageNumber: Int): File {
        // use current time to make file name more unique
        val dateTime: String = LocalDateTime.now().toString()

        // create file in pictures directory
        val storageDir: File? = activity.filesDir
        return File.createTempFile(
            "DOCUMENT_SCAN_${pageNumber}_${dateTime}",
            ".jpg",
            storageDir
        )
    }
}
