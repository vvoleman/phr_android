package cz.vvoleman.phr.featureEvent.data.repository

import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.featureEvent.data.alarm.receiver.event.EventAlarmContent
import cz.vvoleman.phr.featureEvent.data.alarm.receiver.event.EventAlarmReceiver
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDao
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToDataMapper
import cz.vvoleman.phr.featureEvent.data.mapper.core.EventDataModelToDomainMapper
import cz.vvoleman.phr.featureEvent.data.model.core.EventDataModel
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.ScheduleEventAlarmRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.time.ZoneOffset

class AlarmRepository(
    private val eventDao: EventDao,
    private val eventMapper: EventDataModelToDomainMapper,
    private val eventDataSourceMapper: EventDataSourceModelToDataMapper,
    private val alarmScheduler: AlarmScheduler,
) : ScheduleEventAlarmRepository, DeleteEventAlarmRepository {

    suspend fun getActiveEvents(): List<EventDomainModel> {
        return eventDao.getActive(LocalDateTime.now()).first()
            .map { eventDataSourceMapper.toData(it) }
            .map { eventMapper.toDomain(it) }
    }

    override suspend fun deleteEventAlarm(event: EventDomainModel): Boolean {
        val alarmItems = getAlarmItems(eventMapper.toData(event))

        return alarmItems.all { alarmScheduler.cancel(it) }
    }

    override suspend fun scheduleEventAlarm(event: EventDomainModel): Boolean {
        if (event.startAt.isBefore(LocalDateTime.now())) {
            Log.w("AlarmRepository", "Event '${event.id}' is in the past (${event.startAt}), not scheduling alarm")
            return false
        }

        val alarmItems = getAlarmItems(eventMapper.toData(event))

        return alarmItems.all { alarmScheduler.schedule(it) }
    }

    private fun getAlarmItems(model: EventDataModel): List<AlarmItem> {
        val epochNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val epochStartAt = model.startAt.toEpochSecond(ZoneOffset.UTC)

        val futureReminders = model.reminders.filter { epochStartAt - it > epochNow }
        Log.d("AlarmRepository", "Scheduling ${futureReminders.size} alarms for event '${model.id}'")

        return futureReminders.map { reminder ->
            val triggerAt = model.startAt.minusSeconds(reminder)
            val alarmItem = AlarmItem.Single(
                id = "event-${model.id}-reminder-${reminder}",
                triggerAt = triggerAt,
                content = EventAlarmContent(
                    eventId = model.id,
                    triggerAt = triggerAt.toEpochSecond(ZoneOffset.UTC),
                ),
                receiver = EventAlarmReceiver::class.java
            )

            alarmItem
        }
    }
}
