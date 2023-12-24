package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel

data class AddEditMedicalServiceItemPresentationModel(
    val id: String? = null,
    val facility: MedicalFacilityPresentationModel? = null,
    val serviceId: String? = null,
    val telephone: String? = null,
    val email: String? = null,
)
