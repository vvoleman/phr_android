package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository

class GroupMedicineScheduleUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, List<GroupedItemsDomainModel<MedicineScheduleDomainModel>>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(
        request: String
    ): List<GroupedItemsDomainModel<MedicineScheduleDomainModel>> {
        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request)

        val groupedSchedules = schedules.groupBy {
            val char = it.medicine.name.first().uppercaseChar()

            if (char.isLetter()) {
                char
            } else {
                '-'
            }
        }

        return groupedSchedules.map { (key, value) ->
            GroupedItemsDomainModel(
                key.toString(),
                value.sortedBy { it.medicine.name.uppercase() }
            )
        }
    }
}
