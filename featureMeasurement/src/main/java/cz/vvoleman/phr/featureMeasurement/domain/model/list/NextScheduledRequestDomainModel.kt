package cz.vvoleman.phr.featureMeasurement.domain.model.list

import java.time.LocalDateTime

data class NextScheduledRequestDomainModel(
    val patientId: String,
    val currentLocalDateTime: LocalDateTime = LocalDateTime.now(),
    val limit: Int? = 5,
)
