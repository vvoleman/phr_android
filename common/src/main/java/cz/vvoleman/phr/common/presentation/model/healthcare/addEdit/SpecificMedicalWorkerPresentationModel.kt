package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel

data class SpecificMedicalWorkerPresentationModel(
    val id: String? = null,
    val telephone: String = "",
    val email: String = "",
    val medicalWorker: MedicalWorkerUiModel? = null,
    val medicalService: MedicalServiceUiModel? = null,
)
