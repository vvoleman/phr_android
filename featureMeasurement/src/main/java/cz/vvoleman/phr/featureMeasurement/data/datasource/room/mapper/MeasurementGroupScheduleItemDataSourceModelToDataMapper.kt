package cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.model.addEdit.SaveMeasurementGroupScheduleItemDataModel
import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupScheduleItemDataModel

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

    fun toDataSource(
        model: SaveMeasurementGroupScheduleItemDataModel,
        measurementGroupId: String
    ): MeasurementGroupScheduleItemDataSourceModel {
        return MeasurementGroupScheduleItemDataSourceModel(
            id = model.id?.toInt(),
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            measurementGroupId = measurementGroupId.toInt()
        )
    }
}
