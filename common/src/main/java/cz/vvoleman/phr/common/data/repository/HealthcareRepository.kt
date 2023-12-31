package cz.vvoleman.phr.common.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi.Companion.PAGE_SIZE
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcarePagingSource
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerWithServicesDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class HealthcareRepository(
    private val facilityMapper: MedicalFacilityDataSourceModelToDomainMapper,
    private val api: HealthcareApi,
    private val apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalFacilityDao: MedicalFacilityDao,
    private val medicalServiceDao: MedicalServiceDao,
    private val medicalWorkerWithServicesMapper: MedicalWorkerWithServicesDataSourceModelToDomainMapper,
    private val medicalWorkerMapper: MedicalWorkerDataSourceModelToDomainMapper,
) : GetMedicalWorkersWithServicesRepository,
    GetFacilitiesPagingStreamRepository,
    SaveMedicalFacilityRepository,
    SaveMedicalWorkerRepository,
    GetFacilityByIdRepository {

    override fun getFacilitiesPagingStream(query: String): Flow<PagingData<MedicalFacilityDomainModel>> {
        Log.d("HealthcareRepository", "getFacilitiesPagingStream: $query")
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                HealthcarePagingSource(
                    facilityMapper,
                    apiModelToDbMapper,
                    api,
                    query
                )
            }
        ).flow
    }

    override suspend fun getMedicalWorkersWithServices(patientId: String): List<MedicalWorkerWithServicesDomainModel> {
        val workers = medicalWorkerDao.getAll(patientId).first()

        return medicalWorkerWithServicesMapper.toDomain(workers)
    }

    override suspend fun getMedicalWorkerWithServices(medicalWorkerId: String): MedicalWorkerWithServicesDomainModel? {
        val worker = medicalWorkerDao.getById(medicalWorkerId.toInt()).firstOrNull()

        return worker?.let {
            medicalWorkerWithServicesMapper.toDomain(listOf(it)).firstOrNull()
        }
    }

    override suspend fun saveMedicalFacility(facility: MedicalFacilityDomainModel) {
        facilityMapper.toDataSource(facility).let {
            medicalFacilityDao.insert(it.medicalFacility)
            medicalServiceDao.insertAll(it.services)
        }
    }

    override suspend fun saveMedicalFacility(facilities: List<MedicalFacilityDomainModel>) {
        facilities.map { facilityMapper.toDataSource(it) }.let { list ->
            val facilitiesList = list.map { it.medicalFacility }
            val services = list.map { it.services }.flatten()
            medicalFacilityDao.insertAll(facilitiesList)
            medicalServiceDao.insertAll(services)
        }
    }

    override suspend fun saveMedicalWorker(worker: MedicalWorkerDomainModel): String {
        val workerDataSource = medicalWorkerMapper.toDataSource(worker)
        return medicalWorkerDao.insert(workerDataSource).toString()
    }

    override suspend fun getFacilityById(id: String): MedicalFacilityDomainModel? {
        return medicalFacilityDao.getById(id.toInt()).firstOrNull()?.let { facilityMapper.toDomain(it) }
    }
}
