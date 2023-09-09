package cz.vvoleman.phr.feature_medicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.ScheduledByMedicineRequestDomainModel
import cz.vvoleman.phr.feature_medicine.domain.repository.GetScheduleByMedicineRepository

class GetScheduledByMedicineUseCase(
    private val getScheduleByMedicineRepository: GetScheduleByMedicineRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ScheduledByMedicineRequestDomainModel, MedicineScheduleDomainModel?>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: ScheduledByMedicineRequestDomainModel): MedicineScheduleDomainModel? {
        val schedules = getScheduleByMedicineRepository.getScheduleByMedicine(request.medicineId, request.patientId)

        if (schedules.isEmpty()) {
            return null
        }

        return schedules.first()
    }
}
