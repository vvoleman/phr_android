package cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry

import java.time.LocalDateTime

data class SaveMeasurementGroupEntryDomainModel(
    val id: String?,
    val measurementGroupId: String,
    val values: Map<String, String>,
    val scheduleItemId: String? = null,
    val createdAt: LocalDateTime,
)
