package cz.vvoleman.featureMeasurement.domain.model

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class MeasurementGroupScheduleItemDomainModel(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val values: Map<String, String>
)
