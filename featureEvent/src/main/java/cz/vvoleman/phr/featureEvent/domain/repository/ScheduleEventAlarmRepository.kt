package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

interface ScheduleEventAlarmRepository {

    suspend fun scheduleEventAlarm(event: EventDomainModel)

}
