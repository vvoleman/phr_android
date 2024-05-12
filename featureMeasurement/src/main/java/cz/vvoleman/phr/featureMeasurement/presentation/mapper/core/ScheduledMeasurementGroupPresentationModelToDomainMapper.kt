package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.list.ScheduledMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.ScheduledMeasurementGroupPresentationModel

class ScheduledMeasurementGroupPresentationModelToDomainMapper(
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper
) {

    fun toDomain(model: ScheduledMeasurementGroupPresentationModel): ScheduledMeasurementGroupDomainModel {
        return ScheduledMeasurementGroupDomainModel(
            measurementGroup = measurementGroupMapper.toDomain(model.measurementGroup),
            dateTime = model.dateTime
        )
    }

    fun toDomain(models: List<ScheduledMeasurementGroupPresentationModel>): List<ScheduledMeasurementGroupDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toPresentation(model: ScheduledMeasurementGroupDomainModel): ScheduledMeasurementGroupPresentationModel {
        return ScheduledMeasurementGroupPresentationModel(
            measurementGroup = measurementGroupMapper.toPresentation(model.measurementGroup),
            dateTime = model.dateTime
        )
    }

    fun toPresentation(models: List<ScheduledMeasurementGroupDomainModel>): List<ScheduledMeasurementGroupPresentationModel> {
        return models.map { toPresentation(it) }
    }
}
