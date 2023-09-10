package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.SubstanceAmountDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.SubstanceAmountPresentationModel

class SubstanceAmountPresentationModelToDomainMapper(
    private val substanceMapper: SubstancePresentationModelToDomainMapper
) {
    fun toDomain(model: SubstanceAmountPresentationModel): SubstanceAmountDomainModel {
        return SubstanceAmountDomainModel(
            substance = substanceMapper.toDomain(model.substance),
            amount = model.amount,
            unit = model.unit
        )
    }

    fun toPresentation(model: SubstanceAmountDomainModel): SubstanceAmountPresentationModel {
        return SubstanceAmountPresentationModel(
            substance = substanceMapper.toPresentation(model.substance),
            amount = model.amount,
            unit = model.unit
        )
    }
}
