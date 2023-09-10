package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository

class GetRecordByIdUseCase(
    private val getRecordByIdRepository: GetRecordByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, MedicalRecordDomainModel?>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): MedicalRecordDomainModel? {
        return getRecordByIdRepository.getRecordById(request)
    }
}
