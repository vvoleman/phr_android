package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

interface SaveMedicalFacilityRepository {

    suspend fun saveMedicalFacility(
        facility: MedicalFacilityDomainModel,
    )

    suspend fun saveMedicalFacility(facilities: List<MedicalFacilityDomainModel>)

}
