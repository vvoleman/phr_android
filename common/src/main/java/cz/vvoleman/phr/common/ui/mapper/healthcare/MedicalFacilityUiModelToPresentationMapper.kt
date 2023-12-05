package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel

class MedicalFacilityUiModelToPresentationMapper(
    private val serviceWithWorkersMapper: MedicalServiceWithWorkersUiModelToPresentationMapper
) {

    fun toPresentation(model: MedicalFacilityUiModel): MedicalFacilityPresentationModel {
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

    fun toUi(model: MedicalFacilityPresentationModel): MedicalFacilityUiModel {
        return MedicalFacilityUiModel(
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
            services = model.services.map { serviceWithWorkersMapper.toUi(it) }
        )
    }
}
