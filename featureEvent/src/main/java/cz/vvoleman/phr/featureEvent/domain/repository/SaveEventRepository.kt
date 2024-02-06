package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel

interface SaveEventRepository {

    suspend fun saveEvent(event: SaveEventDomainModel): String

}
