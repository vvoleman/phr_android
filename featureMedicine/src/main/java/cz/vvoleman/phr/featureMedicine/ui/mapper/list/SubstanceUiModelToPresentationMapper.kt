package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.featureMedicine.presentation.model.list.SubstancePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.SubstanceUiModel

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
