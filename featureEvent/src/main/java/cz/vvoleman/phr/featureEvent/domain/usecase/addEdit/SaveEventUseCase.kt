package cz.vvoleman.phr.featureEvent.domain.usecase.addEdit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import cz.vvoleman.phr.featureEvent.domain.repository.SaveEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.ScheduleEventAlarmRepository

class SaveEventUseCase(
    private val saveEventRepository: SaveEventRepository,
    private val getEventByIdRepository: GetEventByIdRepository,
    private val deleteEventAlarmRepository: DeleteEventAlarmRepository,
    private val scheduleEventAlarmRepository: ScheduleEventAlarmRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SaveEventDomainModel, EventDomainModel?>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveEventDomainModel): EventDomainModel? {
        val id = saveEventRepository.saveEvent(request)
        val model = getEventByIdRepository.getEventById(id) ?: return null

        if (request.id != null) {
            deleteEventAlarmRepository.deleteEventAlarm(model)
        }

        scheduleEventAlarmRepository.scheduleEventAlarm(model)

        return model
    }
}
