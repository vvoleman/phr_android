package cz.vvoleman.phr.featureMedicalRecord.presentation.model.core

import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.DiagnosePresentationModel
import java.time.LocalDate

data class MedicalRecordPresentationModel(
    val id: String,
    val patient: PatientPresentationModel,
    val createdAt: LocalDate,
    val problemCategory: ProblemCategoryPresentationModel? = null,
    val diagnose: DiagnosePresentationModel? = null,
    val visitDate: LocalDate,
    val specificMedicalWorker: SpecificMedicalWorkerPresentationModel? = null,
    val assets: List<MedicalRecordAssetPresentationModel> = emptyList()
)
