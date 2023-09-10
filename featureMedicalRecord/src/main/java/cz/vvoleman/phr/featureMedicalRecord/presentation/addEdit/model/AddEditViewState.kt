package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

import android.os.Parcelable
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AddEditViewState(
    val recordId: String? = null,
    val createdAt: LocalDate? = null,
    val diagnoseId: String? = null,
    val medicalWorkerId: String? = null,
    val problemCategoryId: String? = null,
    val patientId: String? = null,
    val visitDate: LocalDate? = null,
    val diagnosePage: Int = 1,
    val diagnoseSpinnerList: List<DiagnosePresentationModel> = listOf(),
    val allProblemCategories: List<ProblemCategoryDomainModel> = listOf(),
    val allMedicalWorkers: List<MedicalWorkerDomainModel> = listOf(),
    val assets: List<AssetPresentationModel> = listOf(),
    val saving: Boolean = false
) : Parcelable {

    fun canAddMoreFiles() = assets.size < MAX_FILES

    companion object {
        const val MAX_FILES = 3
        const val TAG = "AddEditViewState"
    }
}
