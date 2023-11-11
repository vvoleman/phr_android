package cz.vvoleman.phr.featureMedicine.data.repository

import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.utils.toEpochSeconds
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmContent
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmReceiver
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.MedicineScheduleDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.model.schedule.MedicineScheduleDataModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteScheduleAlarmRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ScheduleMedicineRepository
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalTime

class AlarmRepository(
    private val medicineScheduleDao: MedicineScheduleDao,
    private val medicineScheduleDataMapper: MedicineScheduleDataSourceModelToDataMapper,
    private val medicineScheduleMapper: MedicineScheduleDataModelToDomainMapper,
    private val alarmScheduler: AlarmScheduler,
) : ScheduleMedicineRepository, DeleteScheduleAlarmRepository {

    suspend fun getActiveMedicineSchedules(): List<MedicineScheduleDomainModel> {
        return medicineScheduleDao.getActive(true).first()
            .map {
                medicineScheduleDataMapper.toData(it).let { domain ->
                    medicineScheduleMapper.toDomain(domain)
                }
            }
    }

    override fun scheduleMedicine(medicineSchedule: MedicineScheduleDomainModel): Boolean {
        val alarmItems = getAlarmItems(medicineScheduleMapper.toData(medicineSchedule))

        return alarmItems.all { alarmScheduler.schedule(it) }
    }

    override suspend fun deleteScheduleAlarm(medicineSchedule: MedicineScheduleDomainModel): Boolean {
        val alarmItems = getAlarmItems(medicineScheduleMapper.toData(medicineSchedule))

        return alarmItems.all { alarmScheduler.cancel(it) }
    }

    private fun getAlarmItems(medicineSchedule: MedicineScheduleDataModel): List<AlarmItem> {
        val groupedItems = groupSchedulesByTime(medicineSchedule.schedules)

        return groupedItems.map { (time, _) ->

            val alarmItem = AlarmItem(
                id = "medicine-schedule-${medicineSchedule.id!!}",
                triggerAt = time,
                content = MedicineAlarmContent(
                    medicineScheduleId = medicineSchedule.id,
                    triggerAt = time.toEpochSeconds(),
                    alarmDays = getAlarmDays(medicineSchedule.schedules)
                ),
                repeatInterval = AlarmItem.REPEAT_DAY.toLong(),
                receiver = MedicineAlarmReceiver::class.java
            )

            alarmItem
        }

    }

    private fun groupSchedulesByTime(scheduleItems: List<ScheduleItemDataModel>): Map<LocalTime, List<ScheduleItemDataModel>> {
        return scheduleItems.groupBy { it.time }
    }

    private fun getAlarmDays(scheduleItems: List<ScheduleItemDataModel>): List<DayOfWeek> {
        return scheduleItems.groupBy { it.dayOfWeek }.keys.toList()
    }

}