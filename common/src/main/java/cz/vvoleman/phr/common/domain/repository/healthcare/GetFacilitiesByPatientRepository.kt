package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

interface GetFacilitiesByPatientRepository {

    suspend fun getFacilitiesByPatient(patientId: String): List<MedicalFacilityDomainModel>
}
