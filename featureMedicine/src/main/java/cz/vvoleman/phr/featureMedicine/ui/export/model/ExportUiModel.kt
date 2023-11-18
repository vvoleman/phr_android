package cz.vvoleman.phr.featureMedicine.ui.export.model

import java.time.LocalDateTime

data class ExportUiModel(
    val scheduleItemId: String,
    val dateTime: LocalDateTime,
    val quantity: Number,
    val unit: String,
    val medicineId: String,
    val medicineName: String,
    val patientId: String,
    val patientName: String,
) {
    fun getId(): String {
        return scheduleItemId + dateTime.toString()
    }
}
