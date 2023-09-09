package cz.vvoleman.phr.feature_medicine.ui.mapper.add_edit

import cz.vvoleman.phr.feature_medicine.presentation.model.addedit.TimePresentationModel
import cz.vvoleman.phr.feature_medicine.ui.time_selector.TimeUiModel

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
