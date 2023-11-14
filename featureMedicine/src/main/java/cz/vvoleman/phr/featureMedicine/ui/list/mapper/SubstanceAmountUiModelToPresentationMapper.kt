package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.SubstanceAmountPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.SubstanceAmountUiModel

class SubstanceAmountUiModelToPresentationMapper(
    private val substanceMapper: SubstanceUiModelToPresentationMapper
) {
    fun toPresentation(model: SubstanceAmountUiModel): SubstanceAmountPresentationModel {
        return SubstanceAmountPresentationModel(
            substance = substanceMapper.toPresentation(model.substance),
            amount = model.amount,
            unit = model.unit
        )
    }

    fun toUi(model: SubstanceAmountPresentationModel): SubstanceAmountUiModel {
        return SubstanceAmountUiModel(
            substance = substanceMapper.toUi(model.substance),
            amount = model.amount,
            unit = model.unit
        )
    }
}
