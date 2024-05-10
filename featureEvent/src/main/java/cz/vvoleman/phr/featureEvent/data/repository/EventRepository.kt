package cz.vvoleman.phr.featureEvent.data.repository

import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDao
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToDataMapper
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToSaveDomainMapper
import cz.vvoleman.phr.featureEvent.data.mapper.core.EventDataModelToDomainMapper
import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventsByPatientRepository
import cz.vvoleman.phr.featureEvent.domain.repository.SaveEventRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

class EventRepository(
    private val eventDao: EventDao,
    private val saveDomainMapper: EventDataSourceModelToSaveDomainMapper,
    private val eventMapper: EventDataModelToDomainMapper,
    private val eventDataSourceMapper: EventDataSourceModelToDataMapper
) : GetEventByIdRepository, GetEventsByPatientRepository, SaveEventRepository, DeleteEventRepository {

    override suspend fun getEventById(id: String): EventDomainModel? {
        return eventDao.getById(id).firstOrNull()
            ?.let { eventDataSourceMapper.toData(it) }
            ?.let { eventMapper.toDomain(it) }
    }

    override suspend fun getEventsCountByPatient(patientId: String): Int {
        return eventDao.getCount(patientId).first()
    }

    override suspend fun getEventsByPatientInRange(
        patientId: String,
        startAt: LocalDateTime,
        endAt: LocalDateTime?
    ): List<EventDomainModel> {
        return eventDao.getInRange(
            patientId,
            startAt,
            endAt ?: LocalDateTime.MAX
        ).first()
            .map { eventDataSourceMapper.toData(it) }
            .map { eventMapper.toDomain(it) }
    }

    override suspend fun saveEvent(event: SaveEventDomainModel): String {
        val model = saveDomainMapper.toDataSource(event)

        return eventDao.insert(model).toString()
    }

    override suspend fun deleteEvent(event: EventDomainModel) {
        eventDao.delete(event.id)
    }
}
