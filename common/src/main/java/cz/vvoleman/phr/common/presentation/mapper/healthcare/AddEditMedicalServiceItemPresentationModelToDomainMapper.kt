package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.save.AddEditMedicalServiceItemDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalServiceItemPresentationModel

class AddEditMedicalServiceItemPresentationModelToDomainMapper(
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper
) {

    fun toDomain(model: AddEditMedicalServiceItemPresentationModel): AddEditMedicalServiceItemDomainModel {
        return AddEditMedicalServiceItemDomainModel(
            id = model.id,
            facility = facilityMapper.toDomain(model.facility!!),
            serviceId = model.serviceId!!,
            telephone = model.telephone,
            email = model.email
        )
    }

    fun toPresentation(model: AddEditMedicalServiceItemDomainModel): AddEditMedicalServiceItemPresentationModel {
        return AddEditMedicalServiceItemPresentationModel(
            id = model.id,
            facility = facilityMapper.toPresentation(model.facility),
            serviceId = model.serviceId,
            telephone = model.telephone,
            email = model.email
        )
    }

}
