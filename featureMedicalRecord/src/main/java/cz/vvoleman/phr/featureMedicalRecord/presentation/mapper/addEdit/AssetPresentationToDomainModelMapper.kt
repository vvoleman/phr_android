package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit

import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel

class AssetPresentationToDomainModelMapper {

    fun toDomain(model: AssetPresentationModel): AssetDomainModel {
        return AssetDomainModel(
            id = model.id,
            createdAt = model.createdAt,
            uri = model.uri
        )
    }
}
