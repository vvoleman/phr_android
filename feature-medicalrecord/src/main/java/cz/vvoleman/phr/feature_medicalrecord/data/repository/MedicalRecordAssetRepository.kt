package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.MedicalRecordAssetDomainToDataSourceMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordAssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.AddMedicalRecordAssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.CreateMedicalRecordAssetRepository

class MedicalRecordAssetRepository(
    private val medicalRecordAssetDomainToDataSourceMapper: MedicalRecordAssetDomainToDataSourceMapper,
    private val medicalRecordAssetDao: MedicalRecordAssetDao
) : CreateMedicalRecordAssetRepository {

    override suspend fun createMedicalRecordAsset(model: AddMedicalRecordAssetDomainModel): String {
        val dataSourceModel = medicalRecordAssetDomainToDataSourceMapper.toDataSource(model)

        return medicalRecordAssetDao.insert(dataSourceModel).toString()
    }
}