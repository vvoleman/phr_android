package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineByIdRepository

class GetMedicineByIdUseCase(
    private val getMedicineByIdRepository: GetMedicineByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, MedicineDomainModel?>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: String): MedicineDomainModel? {
        return getMedicineByIdRepository.getMedicineById(request)
    }
}
