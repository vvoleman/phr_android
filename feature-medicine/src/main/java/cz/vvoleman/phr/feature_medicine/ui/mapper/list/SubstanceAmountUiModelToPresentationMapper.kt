package cz.vvoleman.phr.feature_medicine.ui.mapper.list

import cz.vvoleman.phr.feature_medicine.presentation.model.list.SubstanceAmountPresentationModel
import cz.vvoleman.phr.feature_medicine.ui.model.list.SubstanceAmountUiModel

class SubstanceAmountUiModelToPresentationMapper(
    private val substanceMapper: SubstanceUiModelToPresentationMapper
) {
    fun toPresentation(model: SubstanceAmountUiModel): SubstanceAmountPresentationModel {
        return SubstanceAmountPresentationModel(
            substance = substanceMapper.toPresentation(model.substance),
            amount = model.amount,
            unit = model.unit,
        )
    }

    fun toUi(model: SubstanceAmountPresentationModel): SubstanceAmountUiModel {
        return SubstanceAmountUiModel(
            substance = substanceMapper.toUi(model.substance),
            amount = model.amount,
            unit = model.unit,
        )
    }
}