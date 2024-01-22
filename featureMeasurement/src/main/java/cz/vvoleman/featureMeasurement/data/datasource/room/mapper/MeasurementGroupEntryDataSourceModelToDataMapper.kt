package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupEntryDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.core.MeasurementGroupEntryDataModel

class MeasurementGroupEntryDataSourceModelToDataMapper {

    fun toData(model: MeasurementGroupEntryDataSourceModel): MeasurementGroupEntryDataModel {
        return MeasurementGroupEntryDataModel(
            id = model.id.toString(),
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId?.toString()
        )
    }

    fun toData(models: List<MeasurementGroupEntryDataSourceModel>): List<MeasurementGroupEntryDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(
        model: MeasurementGroupEntryDataModel,
        measurementGroupId: String
    ): MeasurementGroupEntryDataSourceModel {
        return MeasurementGroupEntryDataSourceModel(
            id = model.id.toIntOrNull(),
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId?.toIntOrNull(),
            measurementGroupId = measurementGroupId.toInt()
        )
    }

    fun toDataSource(
        models: List<MeasurementGroupEntryDataModel>,
        measurementGroupId: String
    ): List<MeasurementGroupEntryDataSourceModel> {
        return models.map { toDataSource(it, measurementGroupId) }
    }

}
