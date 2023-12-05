package cz.vvoleman.phr.common.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi.Companion.PAGE_SIZE
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcarePagingSource
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerWithServicesDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class HealthcareRepository(
    private val facilityMapper: MedicalFacilityDataSourceModelToDomainMapper,
    private val api: HealthcareApi,
    private val apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalWorkerWithServicesMapper: MedicalWorkerWithServicesDataSourceModelToDomainMapper
) : GetMedicalWorkersWithServicesRepository, GetFacilitiesPagingStreamRepository {

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
}
