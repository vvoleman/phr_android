package cz.vvoleman.phr.featureMedicine.presentation.export.model

import java.time.LocalDateTime

data class ExportScheduleItemPresentationModel (
    val id: String,
    val dateTime: LocalDateTime,
    val quantity: Number,
    val unit: String,
)