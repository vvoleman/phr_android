package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel

interface AddEditMedicalRecordRepository {

    suspend fun save(addEditMedicalRecordModel: AddEditDomainModel): String
}
