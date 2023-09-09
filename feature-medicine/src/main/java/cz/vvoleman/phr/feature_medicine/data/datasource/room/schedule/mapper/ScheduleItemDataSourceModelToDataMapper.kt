package cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule.ScheduleItemDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.schedule.ScheduleItemDataModel

class ScheduleItemDataSourceModelToDataMapper {

    fun toData(model: ScheduleItemDataSourceModel): ScheduleItemDataModel {
        return ScheduleItemDataModel(
            dayOfWeek = model.day_of_week,
            time = model.time,
            scheduledAt = model.scheduled_at,
            endingAt = model.ending_at,
            quantity = model.quantity,
            description = model.description
        )
    }

    fun toDataSource(model: ScheduleItemDataModel, scheduleId: String): ScheduleItemDataSourceModel {
        return ScheduleItemDataSourceModel(
            day_of_week = model.dayOfWeek,
            time = model.time,
            scheduled_at = model.scheduledAt,
            ending_at = model.endingAt,
            quantity = model.quantity,
            description = model.description,
            schedule_id = scheduleId.toInt()
        )
    }
}
