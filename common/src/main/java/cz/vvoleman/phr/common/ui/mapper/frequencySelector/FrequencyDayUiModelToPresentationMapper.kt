package cz.vvoleman.phr.common.ui.mapper.frequencySelector

import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.ui.component.frequencySelector.FrequencyDayUiModel

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
