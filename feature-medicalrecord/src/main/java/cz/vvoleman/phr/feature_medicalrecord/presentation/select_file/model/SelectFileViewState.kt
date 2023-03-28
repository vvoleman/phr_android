package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.graphics.Bitmap
import android.net.Uri
import java.time.LocalDate

data class SelectFileViewState(
    val uri: Uri? = null,
    val startedAt: LocalDate? = null,
    val recognizedOptions: RecognizedOptionsPresentationModel? = null,
    val previewUri: Bitmap? = null,
    val selectedOptions: SelectedOptionsPresentationModel? = null,
    val files: List<Uri> = emptyList(),
) {
    fun hasRecognizedOptions() = previewUri != null
    fun isLoading() = startedAt != null
}