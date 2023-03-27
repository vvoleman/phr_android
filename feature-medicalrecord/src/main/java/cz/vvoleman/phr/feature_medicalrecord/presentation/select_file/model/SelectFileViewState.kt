package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectFileViewState(
    val startedAt: LocalDate? = null,
    val recognizedOptions: RecognizedOptionsPresentationModel? = null,
    val previewUri: Uri? = null,
    val selectedOptions: SelectedOptionsPresentationModel? = null
) : Parcelable {
    fun hasRecognizedOptions() = previewUri != null
    fun isLoading() = startedAt != null
}