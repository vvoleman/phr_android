package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository

class GetMedicineScheduleByIdUseCase (
    private val getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, MedicineScheduleDomainModel?>(
    coroutineContextProvider
) {
    override suspend fun executeInBackground(request: String): MedicineScheduleDomainModel? {
        return getMedicineScheduleByIdRepository.getMedicineScheduleById(request)
    }
}