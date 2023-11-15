package cz.vvoleman.phr.featureMedicine.ui.export.mapper

import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.export.model.ExportScheduleItemUiModel

class ExportScheduleItemUiModelToPresentationMapper {

    fun toPresentation(model: ExportScheduleItemUiModel): ExportScheduleItemPresentationModel {
        return ExportScheduleItemPresentationModel(
            id = model.id,
            dateTime = model.dateTime,
            quantity = model.quantity,
            unit = model.unit,
        )
    }

    fun toUi(model: ExportScheduleItemPresentationModel): ExportScheduleItemUiModel {
        return ExportScheduleItemUiModel(
            id = model.id,
            dateTime = model.dateTime,
            quantity = model.quantity,
            unit = model.unit,
        )
    }

}