package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface GetRecordByIdRepository {

    suspend fun getRecordById(id: String): MedicalRecordDomainModel?
}
