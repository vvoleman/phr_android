package cz.vvoleman.phr.common.ui.model.healthcare.addEdit

import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel

data class AddEditMedicalServiceItemUiModel(
    val id: String,
    val facility: MedicalFacilityUiModel? = null,
    val serviceId: String? = null,
    val telephone: String? = null,
    val email: String? = null,
)
