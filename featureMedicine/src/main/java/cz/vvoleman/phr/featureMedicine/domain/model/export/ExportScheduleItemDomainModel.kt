package cz.vvoleman.phr.featureMedicine.domain.model.export

import java.time.LocalDateTime

data class ExportScheduleItemDomainModel(
    val id: String,
    val dateTime: LocalDateTime,
    val quantity: Number,
    val unit: String,
)
