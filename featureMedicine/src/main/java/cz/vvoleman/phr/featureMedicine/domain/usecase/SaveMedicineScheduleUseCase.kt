package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.AddMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository

class SaveMedicineScheduleUseCase(
    private val addMedicineRepository: AddMedicineRepository,
    private val saveMedicineScheduleRepository: SaveMedicineScheduleRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<SaveMedicineScheduleDomainModel, String?>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveMedicineScheduleDomainModel): String {
        addMedicineRepository.addMedicine(request.medicine)
        return saveMedicineScheduleRepository.saveMedicineSchedule(request).id
    }
}
