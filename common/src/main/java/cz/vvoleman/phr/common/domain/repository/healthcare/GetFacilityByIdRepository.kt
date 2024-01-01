package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

interface GetFacilityByIdRepository {

    suspend fun getFacilityById(id: String): MedicalFacilityDomainModel?
}
