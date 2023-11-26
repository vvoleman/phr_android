package cz.vvoleman.phr.common.domain.usecase.patient

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.repository.patient.GetAllPatientsRepository

class GetAllPatientsUseCase(
    private val getAllPatientsRepository: GetAllPatientsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, List<PatientDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: Unit): List<PatientDomainModel> {
        return getAllPatientsRepository.getAll()
    }
}
