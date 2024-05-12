package cz.vvoleman.phr.featureMeasurement.data.repository

import android.app.AlarmManager
import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.utils.toEpochSeconds
import cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup.MeasurementGroupAlarmContent
import cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup.MeasurementGroupAlarmReceiver
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.core.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupDataModel
import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupScheduleItemDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteMeasurementGroupAlarmRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.ScheduleMeasurementGroupRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalTime

class AlarmRepository(
    private val measurementGroupDao: MeasurementGroupDao,
    private val measurementGroupDataMapper: MeasurementGroupDataSourceModelToDataMapper,
    private val measurementMapper: MeasurementGroupDataModelToDomainMapper,
    private val alarmScheduler: AlarmScheduler
) : ScheduleMeasurementGroupRepository, DeleteMeasurementGroupAlarmRepository {

    suspend fun getActiveMeasurementGroups(): List<MeasurementGroupDomainModel> {
        return measurementGroupDao.getAll()
            .map {
                measurementGroupDataMapper.toData(it).let { domain ->
                    measurementMapper.toDomain(domain)
                }
            }
            .first()
    }

    override suspend fun scheduleMeasurementGroup(model: MeasurementGroupDomainModel): Boolean {
        Log.d("AlarmRepository", "Scheduling measurement group '${model.id}'")
        val alarmItems = getAlarmItems(measurementMapper.toData(model))

        Log.d("AlarmRepository", "Scheduling $alarmItems alarms")

        return alarmItems.all { alarmScheduler.schedule(it) }
    }

    override suspend fun deleteMeasurementGroupAlarm(model: MeasurementGroupDomainModel): Boolean {
        Log.d("AlarmRepository", "Deleting measurement group '${model.id}'")
        val alarmItems = getAlarmItems(measurementMapper.toData(model))

        return alarmItems.all { alarmScheduler.cancel(it) }
    }

    private fun getAlarmItems(model: MeasurementGroupDataModel): List<AlarmItem> {
        val groupedItems = groupSchedulesByTime(model.scheduleItems)

        return groupedItems.map { (time, _) ->

            val alarmItem = AlarmItem.Repeat(
                id = "measurement-group-${model.id}-${time.toEpochSeconds()}",
                triggerAt = time,
                type = AlarmManager.RTC_WAKEUP,
                content = MeasurementGroupAlarmContent(
                    measurementGroupId = model.id,
                    triggerAt = time.toEpochSeconds(),
                    alarmDays = getAlarmDays(model.scheduleItems)
                ),
                repeatInterval = AlarmItem.Repeat.REPEAT_DAY.toLong(),
                receiver = MeasurementGroupAlarmReceiver::class.java
            )

            alarmItem
        }
    }

    private fun groupSchedulesByTime(
        items: List<MeasurementGroupScheduleItemDataModel>
    ): Map<LocalTime, List<MeasurementGroupScheduleItemDataModel>> {
        return items.groupBy { it.time }
    }

    private fun getAlarmDays(items: List<MeasurementGroupScheduleItemDataModel>): List<DayOfWeek> {
        return items.map { it.dayOfWeek }.distinct()
    }
}
