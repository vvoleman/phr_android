package cz.vvoleman.phr.feature_medicine.presentation.mapper.list

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.SubstanceDomainModel
import cz.vvoleman.phr.feature_medicine.presentation.model.list.SubstancePresentationModel

class SubstancePresentationModelToDomainMapper {
    fun toDomain(model: SubstancePresentationModel): SubstanceDomainModel {
        return SubstanceDomainModel(
            id = model.id,
            name = model.name
        )
    }

    fun toPresentation(model: SubstanceDomainModel): SubstancePresentationModel {
        return SubstancePresentationModel(
            id = model.id,
            name = model.name
        )
    }
}
