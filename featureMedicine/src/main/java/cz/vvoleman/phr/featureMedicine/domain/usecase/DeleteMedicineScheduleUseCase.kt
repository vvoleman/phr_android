package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.DeleteMedicineScheduleResult
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteScheduleAlarmRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository

class DeleteMedicineScheduleUseCase(
    private val getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
    private val deleteMedicineScheduleRepository: DeleteMedicineScheduleRepository,
    private val deleteScheduleAlarmRepository: DeleteScheduleAlarmRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, DeleteMedicineScheduleResult>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): DeleteMedicineScheduleResult {
        val schedule = getMedicineScheduleByIdRepository.getMedicineScheduleById(request)

        return if (schedule != null && deleteMedicineScheduleRepository.deleteMedicineSchedule(schedule)) {
            DeleteMedicineScheduleResult(
                isScheduleDeleted = true,
                isAlarmDeleted = deleteScheduleAlarmRepository.deleteScheduleAlarm(schedule)
            )
        } else {
            DeleteMedicineScheduleResult(
                isScheduleDeleted = false,
                isAlarmDeleted = false
            )
        }
    }
}