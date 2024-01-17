package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

import androidx.paging.PagingData
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class AddEditViewState(
    val recordId: String? = null,
    val createdAt: LocalDate? = null,
    val diagnose: DiagnosePresentationModel? = null,
    val specificMedicalWorkerId: String? = null,
    val problemCategoryId: String? = null,
    val patientId: String? = null,
    val visitDate: LocalDate? = null,
    val query: String = "",
    val diagnoseStream: Flow<PagingData<DiagnosePresentationModel>>? = null,
    val allProblemCategories: List<ProblemCategoryPresentationModel> = listOf(),
    val allMedicalWorkers: List<MedicalWorkerDomainModel> = listOf(),
    val assets: List<AssetPresentationModel> = listOf(),
    val saving: Boolean = false
) {

    fun canAddMoreFiles() = assets.size < MAX_FILES

    companion object {
        const val MAX_FILES = 3
        const val TAG = "AddEditViewState"
    }
}
