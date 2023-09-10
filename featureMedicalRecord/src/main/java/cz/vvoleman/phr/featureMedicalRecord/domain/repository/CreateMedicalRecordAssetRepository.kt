package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.AddMedicalRecordAssetDomainModel

interface CreateMedicalRecordAssetRepository {

    suspend fun createMedicalRecordAsset(model: AddMedicalRecordAssetDomainModel): String
}
