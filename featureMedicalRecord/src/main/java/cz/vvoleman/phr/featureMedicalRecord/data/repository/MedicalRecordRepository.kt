package cz.vvoleman.phr.featureMedicalRecord.data.repository

import android.util.Log
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.AddEditDomainModelToToDataSourceMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.FilterRequestDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.model.FilterRequestDataModel
import cz.vvoleman.phr.featureMedicalRecord.data.model.FilterRequestStateDataModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.MedicalRecordFilterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class MedicalRecordRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
    private val medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper,
    private val addEditDomainModelToToDataSourceMapper: AddEditDomainModelToToDataSourceMapper
) : AddEditMedicalRecordRepository, MedicalRecordFilterRepository, GetRecordByIdRepository {

    override suspend fun save(addEditMedicalRecordModel: AddEditDomainModel): String {
        val model = addEditDomainModelToToDataSourceMapper.toDataSource(addEditMedicalRecordModel)
        val result = medicalRecordDao.insert(model)
        Log.d("MedicalRecordRepository", "result ID: $result")
        return result.toString()
    }

    override suspend fun getRecordById(id: String): MedicalRecordDomainModel? {
        return medicalRecordDao.getById(id).firstOrNull()?.let { medicalRecordDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun filterRecords(request: FilterRequestDomainModel): List<MedicalRecordDomainModel> {
        val filterRequest = filterRequestDomainModelToDataMapper.toData(request)
        Log.d("MedicalRecordRepository", "filterRequest: $filterRequest")

        val state = getFilterRequestState(filterRequest)

        Log.d("MedicalRecordRepository", "state: $state")

        val medicalRecords = when (state) {
            FilterRequestStateDataModel.Category -> medicalRecordDao.filterInCategory(
                filterRequest.patientId,
                filterRequest.sortBy,
                filterRequest.selectedCategoryProblemIds
            )
            FilterRequestStateDataModel.CategoryAndWorker -> medicalRecordDao.filter(
                filterRequest.patientId,
                filterRequest.sortBy,
                filterRequest.selectedMedicalWorkerIds,
                filterRequest.selectedCategoryProblemIds
            )
            FilterRequestStateDataModel.Empty -> medicalRecordDao.getByPatientId(
                filterRequest.patientId,
                filterRequest.sortBy
            )
            FilterRequestStateDataModel.Worker -> medicalRecordDao.filterInWorker(
                filterRequest.patientId,
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
