package cz.vvoleman.phr.common.domain.usecase.patient.addedit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository

class GetPatientByIdUseCase(
    private val getPatientByIdRepository: GetPatientByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, PatientDomainModel?>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): PatientDomainModel? {
        return getPatientByIdRepository.getById(request)
    }
}
