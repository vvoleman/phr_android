package cz.vvoleman.featureMeasurement.ui.component.timeSelector

import cz.vvoleman.featureMeasurement.presentation.model.addEdit.TimePresentationModel
import java.time.LocalTime

class TimeUiModelToPresentationMapper {

    fun toPresentation(model: List<TimeUiModel>): List<LocalTime> {
        return model.map { it.time }
    }

    fun toUi(model: List<TimePresentationModel>): List<TimeUiModel> {
        return model.map { TimeUiModel(time = it.time) }
    }

}
