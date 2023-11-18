package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import java.time.LocalDateTime

data class MedicineSchedulePresentationModel(
    val id: String,
    val patient: PatientPresentationModel,
    val medicine: MedicinePresentationModel,
    val schedules: List<ScheduleItemPresentationModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
)
