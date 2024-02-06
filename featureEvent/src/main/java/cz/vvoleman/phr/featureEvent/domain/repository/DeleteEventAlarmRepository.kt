package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

interface DeleteEventAlarmRepository {

    suspend fun deleteEventAlarm(event: EventDomainModel)

}
