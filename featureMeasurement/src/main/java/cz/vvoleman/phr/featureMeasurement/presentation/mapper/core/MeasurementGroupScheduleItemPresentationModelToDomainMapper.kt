package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupScheduleItemDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupScheduleItemPresentationModel

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
