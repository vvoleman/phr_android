package cz.vvoleman.phr.featureEvent.domain.repository

import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import java.time.LocalDateTime

interface GetEventsByPatientRepository {

    suspend fun getEventsCountByPatient(patientId: String): Int

    suspend fun getEventsByPatientInRange(
        patientId: String,
        startAt: LocalDateTime,
        endAt: LocalDateTime?
    ): List<EventDomainModel>
}
