package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

interface DeleteEventRepository {

    suspend fun deleteEvent(event: EventDomainModel)
}
