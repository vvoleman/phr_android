package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository

class GetNextScheduledUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<NextScheduledRequestDomainModel, List<ScheduleItemWithDetailsDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(
        request: NextScheduledRequestDomainModel
    ): List<ScheduleItemWithDetailsDomainModel> {
        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId).filter {
            !it.isFinished
        }
        val translatedTimes = TranslateDateTimeFacade
            .translate(schedules, request.currentLocalDateTime, 1)

        // Sort by date time
        val sorted = translatedTimes.toSortedMap()

        // Get list of next schedules
        val nextSchedules = mutableListOf<ScheduleItemWithDetailsDomainModel>()
        val values = sorted.values.map { item ->
            item.sortedBy {
                it.medicine.name
            }
        }.flatten()

        var totalSize = request.limit ?: sorted.size

        if (totalSize > values.size) {
            totalSize = values.size
        }

        for (i in 0 until totalSize) {
            val next = values.elementAt(i)

            nextSchedules.add(next)
        }

        return nextSchedules
    }
}
