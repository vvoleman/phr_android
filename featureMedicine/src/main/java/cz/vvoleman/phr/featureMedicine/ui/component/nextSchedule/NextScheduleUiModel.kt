package cz.vvoleman.phr.featureMedicine.ui.component.nextSchedule

import java.time.LocalDateTime

data class NextScheduleUiModel(
    val id: String,
    val time: LocalDateTime,
    val medicineName: String,
    val medicineId: String,
    val quantity: String,
    val unit: String,
)