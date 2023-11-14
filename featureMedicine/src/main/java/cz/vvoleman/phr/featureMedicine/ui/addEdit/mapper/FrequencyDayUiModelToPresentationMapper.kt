package cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper

import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.component.frequencySelector.FrequencyDayUiModel

class FrequencyDayUiModelToPresentationMapper {

    fun toPresentation(model: FrequencyDayUiModel): FrequencyDayPresentationModel {
        return FrequencyDayPresentationModel(
            day = model.day,
            isSelected = model.isSelected
        )
    }

    fun toUi(model: FrequencyDayPresentationModel): FrequencyDayUiModel {
        return FrequencyDayUiModel(
            day = model.day,
            isSelected = model.isSelected
        )
    }

}