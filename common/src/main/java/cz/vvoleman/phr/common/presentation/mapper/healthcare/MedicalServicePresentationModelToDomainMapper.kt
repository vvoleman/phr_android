package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalServicePresentationModel

class MedicalServicePresentationModelToDomainMapper {

    fun toDomain(model: MedicalServicePresentationModel): MedicalServiceDomainModel {
        return MedicalServiceDomainModel(
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

    fun toPresentation(model: MedicalServiceDomainModel): MedicalServicePresentationModel {
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
}
