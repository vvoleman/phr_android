package cz.vvoleman.featureMeasurement.data.model

import java.time.LocalDateTime

data class MeasurementGroupEntryDataModel(
    val id: String,
    val createdAt: LocalDateTime,
    val values: Map<String, String>,
    val scheduleItemId: String? = null
)