package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.FilterRequestDomainModelToDataMapper
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.MedicalRecordDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditMedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MedicalRecordRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
    private val medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper
) : AddEditMedicalRecordRepository, MedicalRecordFilterRepository {

    override suspend fun save(addEditMedicalRecordModel: AddEditMedicalRecordDomainModel): String {
        TODO("Not yet implemented")
    }

    override suspend fun filterRecords(request: FilterRequestDomainModel): List<MedicalRecordDomainModel> {
        val filterRequest = filterRequestDomainModelToDataMapper.toData(request)

        val medicalRecords = medicalRecordDao.filter(
            filterRequest.sortBy,
            filterRequest.sortDirection,
            filterRequest.selectedMedicalWorkerIds,
            filterRequest.selectedCategoryProblemIds
        )

        return medicalRecords.first().map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
    }
}