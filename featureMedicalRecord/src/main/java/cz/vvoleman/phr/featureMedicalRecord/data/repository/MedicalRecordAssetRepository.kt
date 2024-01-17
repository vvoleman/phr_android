package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordAssetDomainToDataSourceMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.AddMedicalRecordAssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateMedicalRecordAssetRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.DeleteUnusedFilesRepository
import kotlinx.coroutines.flow.first

class MedicalRecordAssetRepository(
    private val medicalRecordAssetDomainToDataSourceMapper: MedicalRecordAssetDomainToDataSourceMapper,
    private val medicalRecordAssetDao: MedicalRecordAssetDao
) : CreateMedicalRecordAssetRepository, DeleteUnusedFilesRepository {

    override suspend fun createMedicalRecordAsset(model: AddMedicalRecordAssetDomainModel): String {
        val dataSourceModel = medicalRecordAssetDomainToDataSourceMapper.toDataSource(model)

        return medicalRecordAssetDao.insert(dataSourceModel).toString()
    }

    override suspend fun deleteUnusedFiles(medicalRecordId: String, files: List<String>) {
        val allFiles = medicalRecordAssetDao.getAllForRecord(medicalRecordId.toInt()).first().map { it.id.toString() }

        val unusedFiles: Set<String> = allFiles.toSet() - files.toSet()

        unusedFiles.forEach {
            medicalRecordAssetDao.delete(it.toInt())
        }
    }
}
