package cz.vvoleman.phr.featureMeasurement.data.model.core

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class MeasurementGroupScheduleItemDataModel(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
)
