package cz.vvoleman.featureMeasurement.domain.model.core

import java.time.LocalDateTime

data class MeasurementGroupEntryDomainModel(
    val id: String,
    val createdAt: LocalDateTime,
    val values: Map<String, String>,
    val scheduleItemId: String? = null
)
