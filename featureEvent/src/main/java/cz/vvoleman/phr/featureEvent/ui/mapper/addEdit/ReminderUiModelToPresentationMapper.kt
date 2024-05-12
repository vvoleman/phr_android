package cz.vvoleman.phr.featureEvent.ui.mapper.addEdit

import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.ReminderPresentationModel
import cz.vvoleman.phr.featureEvent.ui.model.addEdit.ReminderUiModel

class ReminderUiModelToPresentationMapper {

    fun toPresentation(model: ReminderUiModel): ReminderPresentationModel {
        return ReminderPresentationModel(
            id = model.id,
            isEnabled = model.isEnabled,
            offset = model.offset
        )
    }

    fun toPresentation(models: List<ReminderUiModel>): List<ReminderPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: ReminderPresentationModel): ReminderUiModel {
        return ReminderUiModel(
            id = model.id,
            isEnabled = model.isEnabled,
            offset = model.offset
        )
    }

    fun toUi(models: List<ReminderPresentationModel>): List<ReminderUiModel> {
        return models.map { toUi(it) }
    }
}
