package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditMedicalRecordDomainModel


interface AddEditMedicalRecordRepository {

    suspend fun save(addEditMedicalRecordModel: AddEditMedicalRecordDomainModel): String

}