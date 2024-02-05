package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.ToggleScheduleAlarmRequest
import cz.vvoleman.phr.featureMedicine.domain.repository.ChangeMedicineScheduleAlarmEnabledRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteScheduleAlarmRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ScheduleMedicineRepository

class ToggleScheduleAlarmUseCase(
    private val changeEnabledRepository: ChangeMedicineScheduleAlarmEnabledRepository,
    private val getScheduleRepository: GetMedicineScheduleByIdRepository,
    private val scheduleRepository: ScheduleMedicineRepository,
    private val deleteRepository: DeleteScheduleAlarmRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ToggleScheduleAlarmRequest, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: ToggleScheduleAlarmRequest): Boolean {
        val schedule = getScheduleRepository.getMedicineScheduleById(request.schedule.medicineScheduleId)
            ?: return false

        changeEnabledRepository.changeMedicineScheduleAlarmEnabled(schedule.id, request.newState)

        return when (request.newState) {
            true -> scheduleRepository.scheduleMedicine(schedule)
            false -> deleteRepository.deleteScheduleAlarm(schedule)
        }
    }
}
