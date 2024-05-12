package cz.vvoleman.phr.common.domain.usecase.healthcare

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.presentation.event.GetMedicalWorkersAdditionalInfoEvent

class GetMedicalWorkersUseCase(
    private val eventBusChannel: EventBusChannel<
        GetMedicalWorkersAdditionalInfoEvent,
        Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>
        >,
    private val getMedicalWorkersRepository: GetMedicalWorkersWithServicesRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<
    GetMedicalWorkersRequest,
    Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>
    >(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: GetMedicalWorkersRequest):
        Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>> {
        Log.d("GetMedicalWorkersUseCase", "executeInBackground")
        val workersWithServices = getMedicalWorkersRepository.getMedicalWorkersWithServices(request.patientId)
        val workers = workersWithServices.map { it.medicalWorker }

        val event = GetMedicalWorkersAdditionalInfoEvent(workers)

        val results = workers.associateWith {
            emptyList<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>()
        }.toMutableMap()

        eventBusChannel.pushEvent(event)
            .filter { it.isNotEmpty() }
            .flatMap { it.entries }
            .onEach {
                if (results.containsKey(it.key)) {
                    results[it.key] = results[it.key]!! + it.value
                } else {
                    results[it.key] = it.value
                }
            }

        return results
    }
}
