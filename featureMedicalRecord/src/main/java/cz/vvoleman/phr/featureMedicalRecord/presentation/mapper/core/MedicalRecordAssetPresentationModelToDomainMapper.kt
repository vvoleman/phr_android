package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordAssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordAssetPresentationModel

class MedicalRecordAssetPresentationModelToDomainMapper {

    fun toDomain(model: MedicalRecordAssetPresentationModel): MedicalRecordAssetDomainModel {
        return MedicalRecordAssetDomainModel(
            id = model.id,
            url = model.url,
            medicalRecordId = model.medicalRecordId,
            createdAt = model.createdAt
        )
    }

    fun toPresentation(model: MedicalRecordAssetDomainModel): MedicalRecordAssetPresentationModel {
        return MedicalRecordAssetPresentationModel(
            id = model.id,
            url = model.url,
            medicalRecordId = model.medicalRecordId,
            createdAt = model.createdAt
        )
    }
}
