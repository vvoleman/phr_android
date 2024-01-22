package cz.vvoleman.featureMeasurement.data.model.addEdit

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class SaveMeasurementGroupScheduleItemDataModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
)
