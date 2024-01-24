package cz.vvoleman.phr.featureMeasurement.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.list.DeleteMeasurementGroupRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntriesByMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteMeasurementGroupAlarmRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteMeasurementGroupRepository

class DeleteMeasurementGroupUseCase(
    private val deleteMeasurementGroupAlarmRepository: DeleteMeasurementGroupAlarmRepository,
    private val deleteEntriesByMeasurementGroupRepository: DeleteEntriesByMeasurementGroupRepository,
    private val deleteMeasurementGroupRepository: DeleteMeasurementGroupRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<DeleteMeasurementGroupRequest, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: DeleteMeasurementGroupRequest) {
        // Delete notifications
        deleteMeasurementGroupAlarmRepository.deleteMeasurementGroupAlarm(request.measurementGroup)

        // First, delete all entries
        deleteEntriesByMeasurementGroupRepository.deleteEntriesByMeasurementGroup(request.measurementGroup.id)

        // Then, delete the measurement group
        deleteMeasurementGroupRepository.deleteMeasurementGroup(request.measurementGroup.id)
    }
}
