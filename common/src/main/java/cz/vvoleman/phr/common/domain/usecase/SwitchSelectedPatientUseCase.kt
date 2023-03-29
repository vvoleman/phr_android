package cz.vvoleman.phr.common.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.SwitchSelectedPatientRepository

class SwitchSelectedPatientUseCase(
    private val switchSelectedPatientRepository: SwitchSelectedPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String) {
        switchSelectedPatientRepository.switchSelectedPatient(request)
    }
}