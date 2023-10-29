package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.factory.TranslateDateTimeFactory
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository

class GetNextScheduledUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<NextScheduledRequestDomainModel, List<MedicineScheduleDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: NextScheduledRequestDomainModel): List<MedicineScheduleDomainModel> {
        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId)
        val translatedTimes = TranslateDateTimeFactory.translate(schedules, request.currentLocalDateTime, 1)

        // Sort by date time
        val sorted = translatedTimes.toSortedMap()

        // Get list of next schedules
        val nextSchedules = mutableListOf<MedicineScheduleDomainModel>()
        val totalSize = request.limit ?: sorted.size
        val values = sorted.values.map { item ->
            item.sortedBy {
                it.id
            }
        }.flatten()

        for (i in 0 until totalSize) {
            val next = values.elementAt(i)

            nextSchedules.add(next)
        }


        return nextSchedules
    }
}