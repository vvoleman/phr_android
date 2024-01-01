package cz.vvoleman.phr.common.domain.event

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

data class GetMedicalFacilitiesAdditionalInfoEvent(
    val medicalFacilities: List<MedicalFacilityDomainModel>,
    val patientId: String
)
