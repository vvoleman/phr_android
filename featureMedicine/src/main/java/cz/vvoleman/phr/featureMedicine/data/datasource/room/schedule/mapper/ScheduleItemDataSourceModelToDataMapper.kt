package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleItemDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel

class ScheduleItemDataSourceModelToDataMapper {

    fun toData(model: ScheduleItemDataSourceModel): ScheduleItemDataModel {
        return ScheduleItemDataModel(
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description
        )
    }

    fun toDataSource(model: ScheduleItemDataModel, scheduleId: String): ScheduleItemDataSourceModel {
        return ScheduleItemDataSourceModel(
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
            endingAt = model.endingAt,
            quantity = model.quantity,
            description = model.description,
            scheduleId = scheduleId.toInt()
        )
    }
}
