package cz.vvoleman.phr.featureMedicalRecord.data.repository

import android.util.Log
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
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
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByFacilityRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByMedicalWorkerRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByProblemCategoryRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.MedicalRecordFilterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class MedicalRecordRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
    private val medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper,
    private val addEditDomainModelToToDataSourceMapper: AddEditDomainModelToToDataSourceMapper
) : AddEditMedicalRecordRepository,
    MedicalRecordFilterRepository,
    GetRecordByIdRepository,
    GetMedicalRecordByMedicalWorkerRepository,
    GetMedicalRecordByFacilityRepository,
    GetMedicalRecordByProblemCategoryRepository {

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

    override suspend fun getMedicalRecordsByMedicalWorker(medicalWorker: MedicalWorkerDomainModel): List<MedicalRecordDomainModel> {
        return medicalRecordDao.getByMedicalWorkerId(medicalWorker.id!!).first()
            .map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun getMedicalRecordsByFacility(
        facilityId: String,
        patientId: String
    ): List<MedicalRecordDomainModel> {
        return medicalRecordDao.getByFacility(facilityId, patientId).first()
            .map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun getMedicalRecordsByFacility(
        facilityIds: List<String>,
        patientId: String
    ): Map<String, List<MedicalRecordDomainModel>> {
        val result = mutableMapOf<String, List<MedicalRecordDomainModel>>()
        medicalRecordDao
            .getByFacility(facilityIds, patientId).first()
            .map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
            .forEach { medicalRecord ->
                if (medicalRecord.specificMedicalWorker == null) return@forEach

                val facilityId = medicalRecord.specificMedicalWorker.medicalService.medicalFacilityId
                val list = result[facilityId]?.toMutableList() ?: mutableListOf()
                list.add(medicalRecord)
                result[facilityId] = list
            }

        return result.toMap()
    }

    override suspend fun getMedicalRecordByProblemCategory(problemCategoryId: String): List<MedicalRecordDomainModel> {
        return medicalRecordDao.getByProblemCategory(problemCategoryId).first()
            .map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun getMedicalRecordByProblemCategory(
        problemCategoryIds: List<String>
    ): Map<String, List<MedicalRecordDomainModel>> {
        val result = mutableMapOf<String, List<MedicalRecordDomainModel>>()
        medicalRecordDao
            .getByProblemCategory(problemCategoryIds).first()
            .map { medicalRecordDataSourceToDomainMapper.toDomain(it) }
            .forEach {
                if (it.problemCategory == null) return@forEach
                result[it.problemCategory.id] =
                    result[it.problemCategory.id]?.toMutableList()?.apply { add(it) } ?: listOf(it)
            }

        return result.toMap()
    }
}
