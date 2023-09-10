package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordAssetDomainToDataSourceMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.AddMedicalRecordAssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateMedicalRecordAssetRepository

class MedicalRecordAssetRepository(
    private val medicalRecordAssetDomainToDataSourceMapper: MedicalRecordAssetDomainToDataSourceMapper,
    private val medicalRecordAssetDao: MedicalRecordAssetDao
) : CreateMedicalRecordAssetRepository {

    override suspend fun createMedicalRecordAsset(model: AddMedicalRecordAssetDomainModel): String {
        val dataSourceModel = medicalRecordAssetDomainToDataSourceMapper.toDataSource(model)

        return medicalRecordAssetDao.insert(dataSourceModel).toString()
    }
}
