package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.SubstancePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.SubstanceUiModel

class SubstanceUiModelToPresentationMapper {
    fun toPresentation(model: SubstanceUiModel): SubstancePresentationModel {
        return SubstancePresentationModel(
            id = model.id,
            name = model.name
        )
    }

    fun toUi(model: SubstancePresentationModel): SubstanceUiModel {
        return SubstanceUiModel(
            id = model.id,
            name = model.name
        )
    }
}
