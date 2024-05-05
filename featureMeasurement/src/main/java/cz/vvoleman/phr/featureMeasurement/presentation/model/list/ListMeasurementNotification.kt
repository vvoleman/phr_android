package cz.vvoleman.phr.featureMeasurement.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel

sealed class ListMeasurementNotification {
    object NoNextSchedule : ListMeasurementNotification()

    data class OpenNextScheduleDetail(
        val nextSchedule: NextSchedulePresentationModel
    ) : ListMeasurementNotification()
}
