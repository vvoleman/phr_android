package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface GetMedicalRecordByFacilityRepository {

    suspend fun getMedicalRecordsByFacility(facilityId: String, patientId: String): List<MedicalRecordDomainModel>

    suspend fun getMedicalRecordsByFacility(facilityIds: List<String>, patientId: String): Map<String, List<MedicalRecordDomainModel>>

}
