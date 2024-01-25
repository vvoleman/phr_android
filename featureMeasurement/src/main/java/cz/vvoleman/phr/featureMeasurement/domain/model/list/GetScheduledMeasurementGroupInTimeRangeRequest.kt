package cz.vvoleman.phr.featureMeasurement.domain.model.list

import java.time.LocalDateTime

data class GetScheduledMeasurementGroupInTimeRangeRequest(
    val patientId: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)
