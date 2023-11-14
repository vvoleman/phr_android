package cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper

import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.component.timeSelector.TimeUiModel

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
