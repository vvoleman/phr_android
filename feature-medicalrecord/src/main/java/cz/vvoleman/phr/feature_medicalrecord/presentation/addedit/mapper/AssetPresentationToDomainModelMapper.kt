package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AssetPresentationModel

class AssetPresentationToDomainModelMapper {

    fun toDomain(model: AssetPresentationModel): AssetDomainModel {
        return AssetDomainModel(
            id = model.id,
            createdAt = model.createdAt,
            uri = model.uri
        )
    }

}