package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedMedicalWorkersRepository

class GetUsedMedicalWorkersUseCase(
    private val getUsedMedicalWorkersRepository: GetUsedMedicalWorkersRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, List<MedicalWorkerDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): List<MedicalWorkerDomainModel> {
        val specificWorkers = getUsedMedicalWorkersRepository.getUsedMedicalWorkers(request)

        // Only unique workers
        return specificWorkers
            .distinctBy { it.medicalWorker.id }
            .map { it.medicalWorker }
    }
}
