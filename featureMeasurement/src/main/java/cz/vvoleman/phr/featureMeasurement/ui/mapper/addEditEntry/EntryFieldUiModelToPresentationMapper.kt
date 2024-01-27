package cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry

import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.EntryFieldPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.addEditEntry.EntryFieldUiModel

class EntryFieldUiModelToPresentationMapper {

    fun toPresentation(model: EntryFieldUiModel): EntryFieldPresentationModel {
        return EntryFieldPresentationModel(
            fieldId = model.fieldId,
            name = model.name,
            value = model.value,
        )
    }

    fun toPresentation(models: List<EntryFieldUiModel>): List<EntryFieldPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: EntryFieldPresentationModel): EntryFieldUiModel {
        return EntryFieldUiModel(
            fieldId = model.fieldId,
            name = model.name,
            value = model.value,
        )
    }

    fun toUi(models: List<EntryFieldPresentationModel>): List<EntryFieldUiModel> {
        return models.map { toUi(it) }
    }

}
