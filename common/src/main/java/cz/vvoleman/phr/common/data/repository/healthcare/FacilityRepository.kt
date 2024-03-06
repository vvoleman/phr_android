package cz.vvoleman.phr.common.data.repository.healthcare

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcarePagingSource
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesByPatientRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FacilityRepository(
    private val facilityMapper: MedicalFacilityDataSourceModelToDomainMapper,
    private val api: HealthcareApi,
    private val apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
    private val medicalFacilityDao: MedicalFacilityDao,
    private val medicalServiceDao: MedicalServiceDao,
) : GetFacilitiesPagingStreamRepository,
    SaveMedicalFacilityRepository,
    GetFacilityByIdRepository,
    GetFacilitiesByPatientRepository {

    override fun getFacilitiesPagingStream(query: String): Flow<PagingData<MedicalFacilityDomainModel>> {
        Log.d("HealthcareRepository", "getFacilitiesPagingStream: $query")
        return Pager(
            config = PagingConfig(pageSize = HealthcareApi.PAGE_SIZE, enablePlaceholders = false),
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

    override suspend fun getFacilityById(id: String): MedicalFacilityDomainModel? {
        return medicalFacilityDao.getById(id).firstOrNull()?.let { facilityMapper.toDomain(it) }
    }

    override suspend fun getFacilityById(ids: List<String>): List<MedicalFacilityDomainModel> {
        return medicalFacilityDao.getById(ids)
            .firstOrNull()
            ?.map { facilityMapper.toDomain(it) }
            ?: emptyList()
    }

    override suspend fun getFacilitiesByPatient(patientId: String): List<MedicalFacilityDomainModel> {
        return medicalFacilityDao.getByPatient(patientId)
            .firstOrNull()
            ?.map { facilityMapper.toDomain(it) }
            ?: emptyList()
    }
}
