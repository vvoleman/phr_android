package cz.vvoleman.phr.featureMeasurement.ui.model.core

import java.time.LocalDateTime

data class ScheduledMeasurementGroupUiModel(
    val measurementGroup: MeasurementGroupUiModel,
    val dateTime: LocalDateTime
)
