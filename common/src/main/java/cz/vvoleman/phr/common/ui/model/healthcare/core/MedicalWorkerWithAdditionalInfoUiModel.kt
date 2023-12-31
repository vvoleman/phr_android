package cz.vvoleman.phr.common.ui.model.healthcare.core

data class MedicalWorkerWithAdditionalInfoUiModel(
    val medicalWorker: MedicalWorkerUiModel,
    val info: List<AdditionalInfoUiModel<MedicalWorkerUiModel>>
)
