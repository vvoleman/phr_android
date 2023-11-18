package cz.vvoleman.phr.featureMedicine.presentation.export.mapper

import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportScheduleItemPresentationModel

class ExportScheduleItemPresentationModelToDomainMapper {

    fun toDomain(model: ExportScheduleItemPresentationModel): ExportScheduleItemDomainModel {
        return ExportScheduleItemDomainModel(
            id = model.id,
            dateTime = model.dateTime,
            quantity = model.quantity,
            unit = model.unit,
        )
    }

    fun toPresentation(model: ExportScheduleItemDomainModel): ExportScheduleItemPresentationModel {
        return ExportScheduleItemPresentationModel(
            id = model.id,
            dateTime = model.dateTime,
            quantity = model.quantity,
            unit = model.unit,
        )
    }
}
