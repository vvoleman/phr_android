package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityWithDetailsDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.MedicalFacilityDataSourceApiModel

class MedicalFacilityApiModelToDbMapper {

    fun toDb(model: MedicalFacilityDataSourceApiModel): MedicalFacilityWithDetailsDataSourceModel {
        return MedicalFacilityWithDetailsDataSourceModel(
            medicalFacility = MedicalFacilityDataSourceModel(
                id = model.id,
                fullName = model.fullName,
                facilityType = model.facilityType,
                street = model.street,
                houseNumber = model.houseNumberOrientation,
                zipCode = model.postalCode,
                city = model.city,
                region = model.region,
                regionCode = model.regionCode,
                district = model.district,
                districtCode = model.districtCode,
                telephone = model.providerTelephone,
                email = model.providerEmail,
                web = model.providerWeb,
                ico = model.identificationNumber,
                providerType = model.providerType,
                providerName = model.providerName,
                gps = model.gps,
            ),
            services = model.services.map {
                MedicalServiceDataSourceModel(
                    careField = it.careField,
                    careForm = it.careForm,
                    careType = it.careType,
                    careExtent = it.careExtent,
                    bedCount = it.bedCount,
                    serviceNote = it.serviceNote,
                    professionalRepresentative = it.professionalRepresentative,
                    medicalFacilityId = it.facilityId
                )
            }
        )
    }
}
