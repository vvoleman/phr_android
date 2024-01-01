package cz.vvoleman.phr.common.ui.model.healthcare.core

data class MedicalFacilityWithAdditionalInfoUiModel(
    val medicalFacility: MedicalFacilityUiModel,
    val info: List<AdditionalInfoUiModel<MedicalFacilityUiModel>>
)
