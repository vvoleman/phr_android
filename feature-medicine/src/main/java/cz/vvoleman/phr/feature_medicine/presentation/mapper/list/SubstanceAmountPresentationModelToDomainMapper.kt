package cz.vvoleman.phr.feature_medicine.presentation.mapper.list

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.SubstanceAmountDomainModel
import cz.vvoleman.phr.feature_medicine.presentation.model.list.SubstanceAmountPresentationModel

class SubstanceAmountPresentationModelToDomainMapper(
    private val substanceMapper: SubstancePresentationModelToDomainMapper
) {
    fun toDomain(model: SubstanceAmountPresentationModel): SubstanceAmountDomainModel {
        return SubstanceAmountDomainModel(
            substance = substanceMapper.toDomain(model.substance),
            amount = model.amount,
            unit = model.unit,
        )
    }

    fun toPresentation(model: SubstanceAmountDomainModel): SubstanceAmountPresentationModel {
        return SubstanceAmountPresentationModel(
            substance = substanceMapper.toPresentation(model.substance),
            amount = model.amount,
            unit = model.unit,
        )
    }
}