package cz.vvoleman.featureMeasurement.presentation.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupScheduleItemDomainModel
import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupScheduleItemPresentationModel

class MeasurementGroupScheduleItemPresentationModelToDomainMapper {

    fun toDomain(model: MeasurementGroupScheduleItemPresentationModel): MeasurementGroupScheduleItemDomainModel {
        return MeasurementGroupScheduleItemDomainModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toPresentation(model: MeasurementGroupScheduleItemDomainModel): MeasurementGroupScheduleItemPresentationModel {
        return MeasurementGroupScheduleItemPresentationModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

}
