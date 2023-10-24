package cz.vvoleman.phr.featureMedicine.domain.model.timeline

import java.time.LocalDateTime

data class NextScheduledRequestDomainModel(
    val patientId: String,
    val currentLocalDateTime: LocalDateTime = LocalDateTime.now(),
    val limit: Int? = 5,
)
