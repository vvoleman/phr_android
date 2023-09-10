package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel

interface MedicalRecordFilterRepository {

    suspend fun filterRecords(request: FilterRequestDomainModel): List<MedicalRecordDomainModel>
}
