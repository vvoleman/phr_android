package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel

class MedicalFacilityPresentationModelToDomainMapper(
    private val serviceWithWorkersMapper: MedicalServiceWithWorkersPresentationModelToDomainMapper
) {

    fun toDomain(model: MedicalFacilityPresentationModel): MedicalFacilityDomainModel {
        return MedicalFacilityDomainModel(
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
            services = model.services.map { serviceWithWorkersMapper.toDomain(it) }
        )
    }

    fun toPresentation(model: MedicalFacilityDomainModel): MedicalFacilityPresentationModel {
        return MedicalFacilityPresentationModel(
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
            services = model.services.map { serviceWithWorkersMapper.toPresentation(it) }
        )
    }
}
