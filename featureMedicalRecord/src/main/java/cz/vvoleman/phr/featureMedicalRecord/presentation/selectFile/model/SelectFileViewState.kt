package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model

import android.graphics.Bitmap
import android.net.Uri
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AssetPresentationModel
import java.time.LocalDate

data class SelectFileViewState(
    val asset: AssetPresentationModel? = null,
    val startedAt: LocalDate? = null,
    val recognizedOptions: RecognizedOptionsPresentationModel? = null,
    val previewUri: Bitmap? = null,
    val selectedOptions: SelectedOptionsPresentationModel? = null,
    val files: List<Uri> = emptyList()
) {
    fun hasRecognizedOptions() = previewUri != null
    fun isLoading() = startedAt != null
}
