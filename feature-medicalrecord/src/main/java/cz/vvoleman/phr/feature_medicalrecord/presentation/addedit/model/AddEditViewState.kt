package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AddEditViewState(
    val recordId: String? = null,
    val createdAt: LocalDate? = null,
    val diagnose: DiagnosePresentationModel? = null,
    val medicalWorkerPresentationModel: MedicalWorkerPresentationModel? = null,
    val problemCategory: ProblemCategoryPresentationModel? = null,
    val patient: PatientPresentationModel?= null,
    val visitDate: LocalDate? = null,
    val files: List<Uri> = listOf()
): Parcelable {


    fun canAddMoreFiles() = files.size < MAX_FILES

    companion object {
        val MAX_FILES = 1
    }
}