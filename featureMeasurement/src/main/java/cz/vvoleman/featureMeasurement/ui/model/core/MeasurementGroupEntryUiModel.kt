package cz.vvoleman.featureMeasurement.ui.model.core

import java.time.LocalDateTime

data class MeasurementGroupEntryUiModel(
    val id: String,
    val createdAt: LocalDateTime,
    val values: Map<String, String>,
    val scheduleItemId: String? = null
)
