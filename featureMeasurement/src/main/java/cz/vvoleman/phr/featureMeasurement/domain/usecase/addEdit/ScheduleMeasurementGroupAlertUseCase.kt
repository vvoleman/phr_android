package cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit

import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteMeasurementGroupAlarmRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.ScheduleMeasurementGroupRepository
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase

class ScheduleMeasurementGroupAlertUseCase (
    private val scheduleMeasurementGroupRepository: ScheduleMeasurementGroupRepository,
    private val deleteMeasurementGroupAlarmRepository: DeleteMeasurementGroupAlarmRepository,
    private val getMeasurementGroupByIdRepository: GetMeasurementGroupRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): Boolean {
        val measurementGroup = getMeasurementGroupByIdRepository.getMeasurementGroup(request) ?: return false

        // Delete old alarm
        deleteMeasurementGroupAlarmRepository.deleteMeasurementGroupAlarm(measurementGroup)

        // Schedule new alarm
        return scheduleMeasurementGroupRepository.scheduleMeasurementGroup(measurementGroup)
    }
}
