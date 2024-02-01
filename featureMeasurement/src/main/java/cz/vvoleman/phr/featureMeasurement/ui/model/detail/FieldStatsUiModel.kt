package cz.vvoleman.phr.featureMeasurement.ui.model.detail

import java.time.LocalDateTime

data class FieldStatsUiModel(
    val fieldId: String,
    val name: String,
    val unit: String,
    val values: Map<LocalDateTime, Float>,
    val minValue: Float?,
    val maxValue: Float?,
    val weekAvgValue: Float?,
)
