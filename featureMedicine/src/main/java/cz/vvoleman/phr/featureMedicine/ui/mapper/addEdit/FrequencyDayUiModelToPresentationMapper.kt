package cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.frequencySelector.FrequencyDayUiModel

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