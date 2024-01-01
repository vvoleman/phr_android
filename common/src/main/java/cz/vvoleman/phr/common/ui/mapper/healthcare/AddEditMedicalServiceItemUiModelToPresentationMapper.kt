package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalServiceItemPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel

class AddEditMedicalServiceItemUiModelToPresentationMapper(
    private val facilityMapper: MedicalFacilityUiModelToPresentationMapper
) {

    fun toPresentation(model: AddEditMedicalServiceItemUiModel): AddEditMedicalServiceItemPresentationModel {
        return AddEditMedicalServiceItemPresentationModel(
            id = model.id,
            facility = model.facility?.let { facilityMapper.toPresentation(it) },
            serviceId = model.serviceId,
            telephone = model.telephone,
            email = model.email
        )
    }

    fun toUi(model: AddEditMedicalServiceItemPresentationModel): AddEditMedicalServiceItemUiModel {
        return AddEditMedicalServiceItemUiModel(
            id = model.id!!,
            facility = model.facility?.let { facilityMapper.toUi(it) },
            serviceId = model.serviceId,
            telephone = model.telephone,
            email = model.email
        )
    }
}
