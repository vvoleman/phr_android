package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.AddMedicalRecordAssetDomainModel

interface CreateMedicalRecordAssetRepository {

    suspend fun createMedicalRecordAsset(model: AddMedicalRecordAssetDomainModel): String
}
