package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequest
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

class GetScheduledInTimeRangeUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SchedulesInRangeRequest, List<ScheduleItemWithDetailsDomainModel>>(
    coroutineContextProvider
) {

    @Suppress("MagicNumber")
    override suspend fun executeInBackground(
        request: SchedulesInRangeRequest
    ): List<ScheduleItemWithDetailsDomainModel> {
        require(!request.startAt.isAfter(request.endAt))

        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId).filter {
            !it.isFinished
        }
        val daysBetween = ChronoUnit.DAYS.between(request.startAt.toLocalDate(), request.endAt.toLocalDate())
        var numberOfWeeks = (daysBetween / 7f)
        numberOfWeeks = if (numberOfWeeks < 1) {
            1f
        } else {
            ceil(numberOfWeeks) + 1
        }

        val translatedSchedules = TranslateDateTimeFacade.translate(schedules, request.startAt, numberOfWeeks.toInt())

        val results = mutableListOf<ScheduleItemWithDetailsDomainModel>()

        for ((key, value) in translatedSchedules) {
            if (key.isBefore(request.startAt) || key.isAfter(request.endAt)) {
                continue
            }

            results.addAll(value)
        }

        return results
    }
}
