package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel

interface GetRecordByIdRepository {

    fun getRecordById(id: String): MedicalRecordDomainModel?

}