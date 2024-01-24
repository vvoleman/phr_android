package cz.vvoleman.phr.featureMeasurement.presentation.model.core

import java.time.LocalDateTime

data class ScheduledMeasurementGroupPresentationModel(
    val measurementGroup: MeasurementGroupPresentationModel,
    val dateTime: LocalDateTime
)
