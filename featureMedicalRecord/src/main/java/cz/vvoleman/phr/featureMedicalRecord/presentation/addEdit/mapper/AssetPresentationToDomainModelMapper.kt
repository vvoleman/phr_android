package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AssetPresentationModel

class AssetPresentationToDomainModelMapper {

    fun toDomain(model: AssetPresentationModel): AssetDomainModel {
        return AssetDomainModel(
            id = model.id,
            createdAt = model.createdAt,
            uri = model.uri
        )
    }
}
