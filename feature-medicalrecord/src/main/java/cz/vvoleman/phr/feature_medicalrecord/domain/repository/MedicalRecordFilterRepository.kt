package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.FilterRequestDomainModel

interface MedicalRecordFilterRepository {

    suspend fun filterRecords(request: FilterRequestDomainModel): List<MedicalRecordDomainModel>

}