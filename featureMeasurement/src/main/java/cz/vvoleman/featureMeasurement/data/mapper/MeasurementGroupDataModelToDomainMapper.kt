package cz.vvoleman.featureMeasurement.data.mapper

import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupDataModel
import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

class MeasurementGroupDataModelToDomainMapper(
    private val fieldMapper: MeasurementGroupFieldDataToDomainMapper,
    private val scheduleItemMapper: MeasurementGroupScheduleItemDataModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryDataModelToDomainMapper,
) {

    fun toDomain(model: MeasurementGroupDataModel): MeasurementGroupDomainModel {
        return MeasurementGroupDomainModel(
            id = model.id,
            name = model.name,
            patient = model.patient,
            scheduleItems = scheduleItemMapper.toDomain(model.scheduleItems),
            fields = fieldMapper.toDomain(model.fields),
            entries = entryMapper.toDomain(model.entries),
        )
    }

    fun toDomain(models: List<MeasurementGroupDataModel>): List<MeasurementGroupDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: MeasurementGroupDomainModel): MeasurementGroupDataModel {
        return MeasurementGroupDataModel(
            id = model.id,
            name = model.name,
            patient = model.patient,
            scheduleItems = scheduleItemMapper.toData(model.scheduleItems),
            fields = fieldMapper.toData(model.fields),
            entries = entryMapper.toData(model.entries),
        )
    }

    fun toData(models: List<MeasurementGroupDomainModel>): List<MeasurementGroupDataModel> {
        return models.map { toData(it) }
    }

}
