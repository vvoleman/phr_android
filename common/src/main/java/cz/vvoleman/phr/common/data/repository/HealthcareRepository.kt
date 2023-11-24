package cz.vvoleman.phr.common.data.repository

import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper

class HealthcareRepository(
    private val api: HealthcareApi,
    private val apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
    private val medicalFacilityDao: MedicalFacilityDao,
    private val medicalServiceDao: MedicalServiceDao,
) {

    suspend fun getMedicalFacilities(page: Int, fullName: String = "", city: String = ""): List<MedicalFacilityDataSourceModel> {
        val response = api.getFacilities(page, fullName, city)
        if (response.status == "success") {
            val medicalFacilities = response.data.map { apiModelToDbMapper.toDb(it) }

            // Loop through and get the services for each facility and facility
            val services = mutableListOf<MedicalServiceDataSourceModel>()
            val facilities = mutableListOf<MedicalFacilityDataSourceModel>()
            medicalFacilities.forEach { facility ->
                services.addAll(facility.services)
                facilities.add(facility.medicalFacility)
            }

            medicalFacilityDao.insertAll(facilities)
            medicalServiceDao.insertAll(services)

            return facilities
        }

        return emptyList()
    }
}
