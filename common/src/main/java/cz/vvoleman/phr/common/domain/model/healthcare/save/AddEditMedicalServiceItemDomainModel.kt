package cz.vvoleman.phr.common.domain.model.healthcare.save

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

data class AddEditMedicalServiceItemDomainModel(
    val id: String? = null,
    val facility: MedicalFacilityDomainModel,
    val serviceId: String,
    val telephone: String? = null,
    val email: String? = null,
)
