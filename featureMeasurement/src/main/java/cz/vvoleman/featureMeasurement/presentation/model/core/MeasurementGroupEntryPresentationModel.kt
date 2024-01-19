package cz.vvoleman.featureMeasurement.presentation.model.core

import java.time.LocalDateTime

data class MeasurementGroupEntryPresentationModel(
    val id: String,
    val createdAt: LocalDateTime,
    val values: Map<String, String>,
    val scheduleItemId: String? = null
)
