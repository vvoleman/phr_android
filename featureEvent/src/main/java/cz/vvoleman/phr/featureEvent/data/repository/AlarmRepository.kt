package cz.vvoleman.phr.featureEvent.data.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.ScheduleEventAlarmRepository

class AlarmRepository : ScheduleEventAlarmRepository, DeleteEventAlarmRepository {

    override suspend fun deleteEventAlarm(event: EventDomainModel) {
    }

    override suspend fun scheduleEventAlarm(event: EventDomainModel) {
    }
}
