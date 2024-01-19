package cz.vvoleman.featureMeasurement.ui.model.core

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class MeasurementGroupScheduleItemUiModel(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
)
