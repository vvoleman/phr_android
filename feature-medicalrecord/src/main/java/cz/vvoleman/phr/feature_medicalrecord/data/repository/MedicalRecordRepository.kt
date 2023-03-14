package cz.vvoleman.phr.feature_medicalrecord.data.repository

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.FilterRequestDomainModelToDataMapper
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.MedicalRecordDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.data.model.FilterRequestDataModel
import cz.vvoleman.phr.feature_medicalrecord.data.model.FilterRequestStateDataModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import kotlinx.coroutines.flow.first

class MedicalRecordRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
    private val medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper
) : AddEditMedicalRecordRepository, MedicalRecordFilterRepository {

    override suspend fun save(addEditMedicalRecordModel: AddEditDomainModel): String {
        TODO("Not yet implemented")
    }

    override suspend fun filterRecords(request: FilterRequestDomainModel): List<MedicalRecordDomainModel> {
        val filterRequest = filterRequestDomainModelToDataMapper.toData(request)
        Log.d("MedicalRecordRepository", "filterRequest: $filterRequest")

        val state = getFilterRequestState(filterRequest)

        Log.d("MedicalRecordRepository", "state: $state")

        val medicalRecords = when (state) {
            FilterRequestStateDataModel.Category -> medicalRecordDao.filterInCategory(
                filterRequest.sortBy,
                filterRequest.selectedCategoryProblemIds
            )
            FilterRequestStateDataModel.CategoryAndWorker -> medicalRecordDao.filter(
                filterRequest.sortBy,
                filterRequest.selectedMedicalWorkerIds,
                filterRequest.selectedCategoryProblemIds
            )
            FilterRequestStateDataModel.Empty -> medicalRecordDao.getAll(filterRequest.sortBy)
            FilterRequestStateDataModel.Worker -> medicalRecordDao.filterInWorker(
                filterRequest.sortBy,
                filterRequest.selectedMedicalWorkerIds
            )
        }

        val result = medicalRecords.first().map { medicalRecordDataSourceToDomainMapper.toDomain(it) }

        Log.d("MedicalRecordRepository", "result: $result")

        return result
    }

    private fun getFilterRequestState(filterRequest: FilterRequestDataModel): FilterRequestStateDataModel {
        val hasCategories = filterRequest.selectedCategoryProblemIds.isNotEmpty()
        val hasWorkers = filterRequest.selectedMedicalWorkerIds.isNotEmpty()

        return when {
            hasCategories && hasWorkers -> FilterRequestStateDataModel.CategoryAndWorker
            hasCategories -> FilterRequestStateDataModel.Category
            hasWorkers -> FilterRequestStateDataModel.Worker
            else -> FilterRequestStateDataModel.Empty
        }
    }
}