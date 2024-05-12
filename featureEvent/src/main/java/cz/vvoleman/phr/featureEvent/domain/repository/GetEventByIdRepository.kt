package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

interface GetEventByIdRepository {

    suspend fun getEventById(id: String): EventDomainModel?
}
