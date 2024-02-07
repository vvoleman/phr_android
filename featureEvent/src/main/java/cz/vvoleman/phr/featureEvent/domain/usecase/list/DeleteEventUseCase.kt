package cz.vvoleman.phr.featureEvent.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventRepository

class DeleteEventUseCase(
    private val deleteEventRepository: DeleteEventRepository,
    private val deleteEventAlarmRepository: DeleteEventAlarmRepository,
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<EventDomainModel, Unit>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: EventDomainModel) {
        deleteEventAlarmRepository.deleteEventAlarm(request)
        deleteEventRepository.deleteEvent(request)
    }
}
