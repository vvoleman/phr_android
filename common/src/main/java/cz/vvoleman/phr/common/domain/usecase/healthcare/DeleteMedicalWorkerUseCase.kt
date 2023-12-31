package cz.vvoleman.phr.common.domain.usecase.healthcare

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.event.MedicalWorkerDeletedEvent
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.DeleteMedicalWorkerRepository

class DeleteMedicalWorkerUseCase(
    private val eventBus: EventBusChannel<MedicalWorkerDeletedEvent, Unit>,
    private val deleteMedicalWorkerRepository: DeleteMedicalWorkerRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<MedicalWorkerDomainModel, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: MedicalWorkerDomainModel) {
        val event = MedicalWorkerDeletedEvent(
            medicalWorker = request
        )

        eventBus.pushEvent(event)
        deleteMedicalWorkerRepository.deleteMedicalWorker(request)
    }
}
