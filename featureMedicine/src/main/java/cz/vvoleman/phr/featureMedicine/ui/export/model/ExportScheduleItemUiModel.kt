package cz.vvoleman.phr.featureMedicine.ui.export.model

import java.time.LocalDateTime

data class ExportScheduleItemUiModel(
    val id: String,
    val dateTime: LocalDateTime,
    val quantity: Number,
    val unit: String,
)
