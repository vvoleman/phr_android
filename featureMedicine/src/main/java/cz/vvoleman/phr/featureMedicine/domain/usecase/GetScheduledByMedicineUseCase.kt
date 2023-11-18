package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduledByMedicineRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByMedicineRepository

class GetScheduledByMedicineUseCase(
    private val getSchedulesByMedicineRepository: GetSchedulesByMedicineRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ScheduledByMedicineRequestDomainModel, MedicineScheduleDomainModel?>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(
        request: ScheduledByMedicineRequestDomainModel
    ): MedicineScheduleDomainModel? {
        val schedules = getSchedulesByMedicineRepository.getSchedulesByMedicine(
            listOf(request.medicineId),
            request.patientId
        )

        if (schedules.isEmpty()) {
            return null
        }

        return schedules.first()
    }
}
