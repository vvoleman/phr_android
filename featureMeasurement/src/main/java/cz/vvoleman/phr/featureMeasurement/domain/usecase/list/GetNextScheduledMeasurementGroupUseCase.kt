package cz.vvoleman.phr.featureMeasurement.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository

class GetNextScheduledMeasurementGroupUseCase(
    private val getMeasurementGroupsByPatientRepository: GetMeasurementGroupsByPatientRepository,
    private val translateDateTimeFacade: MeasurementTranslateDateTimeFacade,
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<NextScheduledRequestDomainModel, List<MeasurementGroupDomainModel>>(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(request: NextScheduledRequestDomainModel): List<MeasurementGroupDomainModel> {
        val groups = getMeasurementGroupsByPatientRepository.getMeasurementGroupsByPatient(request.patientId)
        val translatedTimes =
            translateDateTimeFacade.translate(groups, request.currentLocalDateTime, request.limit ?: 5)

        // Sort by date time
        val sorted = translatedTimes.toSortedMap()

        // Get list of next schedules
        val nextSchedules = mutableListOf<MeasurementGroupDomainModel>()
        val values = sorted.values.map { item ->
            item.sortedBy {
                it.name
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
