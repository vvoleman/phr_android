package cz.vvoleman.phr.featureMeasurement.presentation.model.core

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class MeasurementGroupScheduleItemPresentationModel(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
)
