package cz.vvoleman.phr.common.domain.usecase.healthcare

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerAdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository

class GetMedicalWorkersUseCase(
    private val commonEventBus: CommonEventBus,
    private val getMedicalWorkersRepository: GetMedicalWorkersWithServicesRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetMedicalWorkersRequest, Map<MedicalWorkerDomainModel, List<MedicalWorkerAdditionalInfoDomainModel>>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: GetMedicalWorkersRequest):
        Map<MedicalWorkerDomainModel, List<MedicalWorkerAdditionalInfoDomainModel>> {
        val workersWithServices = getMedicalWorkersRepository.getMedicalWorkersWithServices(request.patientId)
        val workers = workersWithServices.map { it.medicalWorker }

        val event = GetMedicalWorkersAdditionalInfoEvent(workers)

        val results = commonEventBus.getWorkerAdditionalInfoBus.pushEvent(event)
        Log.d("GetMedicalWorkersUseCase", "${results.size}")
        return emptyMap()
    }
}
