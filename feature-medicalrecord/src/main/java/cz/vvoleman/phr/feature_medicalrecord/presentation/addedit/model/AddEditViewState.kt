package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import java.time.LocalDate

data class AddEditViewState(
    val recordId: String? = null,
    val createdAt: LocalDate? = null,
    val diagnose: DiagnosePresentationModel? = null,
    val medicalWorkerPresentationModel: MedicalWorkerPresentationModel? = null,
    val problemCategory: ProblemCategoryPresentationModel? = null,
    val patient: PatientPresentationModel?= null,
    val recordIds: List<String> = listOf()
)