package cz.vvoleman.featureMeasurement.data.mapper

import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupEntryDataModel
import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupEntryDomainModel

class MeasurementGroupEntryDataModelToDomainMapper {

    fun toDomain(model: MeasurementGroupEntryDataModel): MeasurementGroupEntryDomainModel {
        return MeasurementGroupEntryDomainModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId
        )
    }

    fun toDomain(models: List<MeasurementGroupEntryDataModel>): List<MeasurementGroupEntryDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: MeasurementGroupEntryDomainModel): MeasurementGroupEntryDataModel {
        return MeasurementGroupEntryDataModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId
        )
    }

    fun toData(models: List<MeasurementGroupEntryDomainModel>): List<MeasurementGroupEntryDataModel> {
        return models.map { toData(it) }
    }

}