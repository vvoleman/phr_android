package cz.vvoleman.phr.featureMeasurement.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GetScheduledMeasurementGroupInTimeRangeRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.ScheduledMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

class GetScheduledMeasurementGroupInTimeRangeUseCase(
    private val translateDateTimeFacade: MeasurementTranslateDateTimeFacade,
    private val getMeasurementGroupsByPatientRepository: GetMeasurementGroupsByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<
        GetScheduledMeasurementGroupInTimeRangeRequest,
        List<ScheduledMeasurementGroupDomainModel>
        >(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(
        request: GetScheduledMeasurementGroupInTimeRangeRequest
    ): List<ScheduledMeasurementGroupDomainModel> {
        require(!request.startAt.isAfter(request.endAt))

        val groups = getMeasurementGroupsByPatientRepository.getMeasurementGroupsByPatient(request.patientId)
        val daysBetween = ChronoUnit.DAYS.between(request.startAt.toLocalDate(), request.endAt.toLocalDate())
        var numberOfWeeks = (daysBetween / TimeConstants.DAYS_IN_WEEK.toFloat())
        numberOfWeeks = if (numberOfWeeks < 1) {
            1f
        } else {
            ceil(numberOfWeeks) + 1
        }

        val translated = translateDateTimeFacade.translate(groups, request.startAt, numberOfWeeks.toInt())

        val results = mutableListOf<ScheduledMeasurementGroupDomainModel>()

        for ((key, value) in translated) {
            if (key.isBefore(request.startAt) || key.isAfter(request.endAt)) {
                continue
            }

            results.addAll(value)
        }

        return results
    }
}
