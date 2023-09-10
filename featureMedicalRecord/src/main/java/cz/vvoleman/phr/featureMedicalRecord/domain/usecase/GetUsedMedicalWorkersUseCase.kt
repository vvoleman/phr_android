package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedMedicalWorkersRepository

class GetUsedMedicalWorkersUseCase(
    private val getUsedMedicalWorkersRepository: GetUsedMedicalWorkersRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, List<MedicalWorkerDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): List<MedicalWorkerDomainModel> {
        return getUsedMedicalWorkersRepository.getUsedMedicalWorkers(request)
    }
}
