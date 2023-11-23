package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityWithDetailsDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

class MedicalFacilityDataSourceModelToDomainMapper(
    private val serviceMapper: MedicalServiceDataSourceModelToDomainMapper,
    private val serviceWithWorkersMapper: MedicalServiceWithWorkersDataSourceModelToDomainMapper,
) {

    suspend fun toDomain(model: MedicalFacilityWithDetailsDataSourceModel): MedicalFacilityDomainModel {
        return MedicalFacilityDomainModel(
            id = model.medicalFacility.id,
            fullName = model.medicalFacility.fullName,
            facilityType = model.medicalFacility.facilityType,
            street = model.medicalFacility.street,
            houseNumber = model.medicalFacility.houseNumber,
            zipCode = model.medicalFacility.zipCode,
            city = model.medicalFacility.city,
            region = model.medicalFacility.region,
            regionCode = model.medicalFacility.regionCode,
            district = model.medicalFacility.district,
            districtCode = model.medicalFacility.districtCode,
            telephone = model.medicalFacility.telephone,
            email = model.medicalFacility.email,
            web = model.medicalFacility.web,
            ico = model.medicalFacility.ico,
            providerType = model.medicalFacility.providerType,
            providerName = model.medicalFacility.providerName,
            gps = model.medicalFacility.gps,
            services = model.services.map { serviceWithWorkersMapper.toDomain(it) }
        )
    }

    fun toDataSource(model: MedicalFacilityDomainModel): MedicalFacilityWithDetailsDataSourceModel {
        return MedicalFacilityWithDetailsDataSourceModel(
            medicalFacility = MedicalFacilityDataSourceModel(
                id = model.id,
                fullName = model.fullName,
                facilityType = model.facilityType,
                street = model.street,
                houseNumber = model.houseNumber,
                zipCode = model.zipCode,
                city = model.city,
                region = model.region,
                regionCode = model.regionCode,
                district = model.district,
                districtCode = model.districtCode,
                telephone = model.telephone,
                email = model.email,
                web = model.web,
                ico = model.ico,
                providerType = model.providerType,
                providerName = model.providerName,
                gps = model.gps,
            ),
            services = model.services.map { serviceMapper.toDataSource(it.medicalService) }
        )
    }
}
