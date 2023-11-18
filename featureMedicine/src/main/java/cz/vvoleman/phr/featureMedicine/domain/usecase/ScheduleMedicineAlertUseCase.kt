package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ScheduleMedicineRepository

class ScheduleMedicineAlertUseCase(
    private val scheduleMedicineRepository: ScheduleMedicineRepository,
    private val getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): Boolean {
        val medicineSchedule = getMedicineScheduleByIdRepository.getMedicineScheduleById(request) ?: return false

        return scheduleMedicineRepository.scheduleMedicine(medicineSchedule)
    }
}
