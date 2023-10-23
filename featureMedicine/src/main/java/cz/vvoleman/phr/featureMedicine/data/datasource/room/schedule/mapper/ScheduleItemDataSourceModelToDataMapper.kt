package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleItemDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel

class ScheduleItemDataSourceModelToDataMapper {

    fun toData(model: ScheduleItemDataSourceModel): ScheduleItemDataModel {
        return ScheduleItemDataModel(
            id = model.id.toString(),
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            unit = model.unit,
            description = model.description
        )
    }

    fun toDataSource(model: ScheduleItemDataModel, scheduleId: String): ScheduleItemDataSourceModel {
        return ScheduleItemDataSourceModel(
            id = model.id?.toInt() ?: 0,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description,
            unit = model.unit,
            scheduleId = scheduleId.toInt()
        )
    }
}
