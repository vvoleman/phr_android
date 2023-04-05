package cz.vvoleman.phr.feature_medicine.domain.model.timeline

import java.time.LocalDateTime

data class SchedulesInRangeRequestDomainModel(
    val patientId: String,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null
)
