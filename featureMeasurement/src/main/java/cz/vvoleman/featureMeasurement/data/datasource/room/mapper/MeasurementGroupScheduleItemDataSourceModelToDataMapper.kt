package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupScheduleItemDataModel

class MeasurementGroupScheduleItemDataSourceModelToDataMapper {

    fun toData(model: MeasurementGroupScheduleItemDataSourceModel): MeasurementGroupScheduleItemDataModel {
        return MeasurementGroupScheduleItemDataModel(
            id = model.id.toString(),
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toData(models: List<MeasurementGroupScheduleItemDataSourceModel>): List<MeasurementGroupScheduleItemDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(
        model: MeasurementGroupScheduleItemDataModel,
        measurementGroupId: String
    ): MeasurementGroupScheduleItemDataSourceModel {
        return MeasurementGroupScheduleItemDataSourceModel(
            id = model.id.toIntOrNull(),
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            measurementGroupId = measurementGroupId.toInt()
        )
    }

    fun toDataSource(
        models: List<MeasurementGroupScheduleItemDataModel>,
        measurementGroupId: String
    ): List<MeasurementGroupScheduleItemDataSourceModel> {
        return models.map { toDataSource(it, measurementGroupId) }
    }

}
