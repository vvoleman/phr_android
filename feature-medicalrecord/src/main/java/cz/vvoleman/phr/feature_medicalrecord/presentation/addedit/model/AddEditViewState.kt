package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AddEditViewState(
    val recordId: String? = null,
    val createdAt: LocalDate? = null,
    val diagnoseId: String? = null,
    val medicalWorkerId: String? = null,
    val problemCategoryId: String? = null,
    val patientId: String?= null,
    val visitDate: LocalDate? = null,
    val diagnosePage: Int = 1,
    val diagnoseSpinnerList: List<DiagnosePresentationModel> = listOf(),
    val files: List<Uri> = listOf()
): Parcelable {


    fun canAddMoreFiles() = files.size < MAX_FILES

    companion object {
        val MAX_FILES = 3
        val TAG = "AddEditViewState"
    }
}