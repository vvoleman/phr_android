package cz.vvoleman.phr.common.domain.usecase.healthcare

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository

class GetMedicalWorkersUseCase(
    private val commonEventBus: CommonEventBus,
    private val getMedicalWorkersRepository: GetMedicalWorkersWithServicesRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetMedicalWorkersRequest, Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: GetMedicalWorkersRequest):
            Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>> {
        Log.d("GetMedicalWorkersUseCase", "executeInBackground")
        val workersWithServices = getMedicalWorkersRepository.getMedicalWorkersWithServices(request.patientId)
        val workers = workersWithServices.map { it.medicalWorker }

        val event = GetMedicalWorkersAdditionalInfoEvent(workers)

        val results = mutableMapOf<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>()

        commonEventBus.getWorkerAdditionalInfoBus.pushEvent(event)
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
