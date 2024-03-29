package cz.vvoleman.phr.featureMedicine.domain.model.timeline

import java.time.LocalDateTime

data class SchedulesInRangeRequestDomainModel(
    val patientId: String,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null
)
