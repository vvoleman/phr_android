package cz.vvoleman.featureMeasurement.data.mapper.core

import cz.vvoleman.featureMeasurement.data.model.core.MeasurementGroupEntryDataModel
import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel

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
