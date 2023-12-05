package cz.vvoleman.phr.common.presentation.model.healthcare.core

data class SpecificMedicalWorkerPresentationModel(
    val id: String? = null,
    val telephone: String = "",
    val email: String = "",
    val medicalWorker: MedicalWorkerPresentationModel? = null,
    val medicalService: MedicalServicePresentationModel? = null,
)
