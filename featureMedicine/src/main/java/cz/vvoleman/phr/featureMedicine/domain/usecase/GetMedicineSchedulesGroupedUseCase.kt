package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupedMedicineSchedulesDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository

class GetMedicineSchedulesGroupedUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, List<GroupedMedicineSchedulesDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): List<GroupedMedicineSchedulesDomainModel> {
        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request)

        val groupedSchedules = schedules.groupBy {
            it.medicine.name.first().uppercaseChar()
        }

        return groupedSchedules.map { (key, value) ->
            GroupedMedicineSchedulesDomainModel(
                groupLabel = key.toString(),
                schedules = value.sortedBy { it.medicine.name.uppercase() }
            )
        }
    }
}