package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.SubstanceDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.SubstancePresentationModel

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
