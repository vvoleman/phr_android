package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalServicePresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceUiModel

class MedicalServiceUiModelToPresentationMapper {

    fun toPresentation(model: MedicalServiceUiModel): MedicalServicePresentationModel {
        return MedicalServicePresentationModel(
            id = model.id,
            careField = model.careField,
            careForm = model.careForm,
            careType = model.careType,
            careExtent = model.careExtent,
            bedCount = model.bedCount,
            serviceNote = model.serviceNote,
            professionalRepresentative = model.professionalRepresentative,
            medicalFacilityId = model.medicalFacilityId
        )
    }

    fun toUi(model: MedicalServicePresentationModel): MedicalServiceUiModel {
        return MedicalServiceUiModel(
            id = model.id,
            careField = model.careField,
            careForm = model.careForm,
            careType = model.careType,
            careExtent = model.careExtent,
            bedCount = model.bedCount,
            serviceNote = model.serviceNote,
            professionalRepresentative = model.professionalRepresentative,
            medicalFacilityId = model.medicalFacilityId
        )
    }
}
