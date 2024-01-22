package cz.vvoleman.phr.featureMeasurement.data.mapper.core

import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupScheduleItemDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupScheduleItemDomainModel

class MeasurementGroupScheduleItemDataModelToDomainMapper {

    fun toDomain(model: MeasurementGroupScheduleItemDataModel): MeasurementGroupScheduleItemDomainModel {
        return MeasurementGroupScheduleItemDomainModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toDomain(models: List<MeasurementGroupScheduleItemDataModel>): List<MeasurementGroupScheduleItemDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: MeasurementGroupScheduleItemDomainModel): MeasurementGroupScheduleItemDataModel {
        return MeasurementGroupScheduleItemDataModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toData(models: List<MeasurementGroupScheduleItemDomainModel>): List<MeasurementGroupScheduleItemDataModel> {
        return models.map { toData(it) }
    }

}