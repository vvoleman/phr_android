package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

interface GetEventsByPatientRepository {

    suspend fun getEventsByPatient(patientId: String): List<EventDomainModel>

}
