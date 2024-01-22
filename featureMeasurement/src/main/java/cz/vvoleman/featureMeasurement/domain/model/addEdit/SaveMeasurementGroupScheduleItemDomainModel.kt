package cz.vvoleman.featureMeasurement.domain.model.addEdit

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class SaveMeasurementGroupScheduleItemDomainModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
)
