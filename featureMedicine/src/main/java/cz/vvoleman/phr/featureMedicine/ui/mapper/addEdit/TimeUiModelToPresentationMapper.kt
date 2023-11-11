package cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.timeSelector.TimeUiModel

class TimeUiModelToPresentationMapper {

    fun toPresentation(uiModel: TimeUiModel): TimePresentationModel {
        return TimePresentationModel(
            time = uiModel.time,
            number = uiModel.number
        )
    }

    fun toUi(presentationModel: TimePresentationModel): TimeUiModel {
        return TimeUiModel(
            time = presentationModel.time,
            number = presentationModel.number
        )
    }
}
