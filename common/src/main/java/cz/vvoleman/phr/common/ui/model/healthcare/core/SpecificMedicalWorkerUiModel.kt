package cz.vvoleman.phr.common.ui.model.healthcare.core

data class SpecificMedicalWorkerUiModel(
    val id: String? = null,
    val telephone: String = "",
    val email: String = "",
    val medicalWorker: MedicalWorkerUiModel? = null,
    val medicalService: MedicalServiceUiModel? = null,
)
