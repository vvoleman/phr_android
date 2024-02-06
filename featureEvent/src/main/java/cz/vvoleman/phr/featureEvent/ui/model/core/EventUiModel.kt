package cz.vvoleman.phr.featureEvent.ui.model.core

import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel
import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel
import cz.vvoleman.phr.featureEvent.domain.model.core.ReminderOffset
import java.time.LocalDateTime

data class EventUiModel(
    val id: String,
    val name: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime?,
    val patient: PatientUiModel,
    val specificMedicalWorker: SpecificMedicalWorkerUiModel?,
    val description: String?,
    val reminders: List<ReminderOffset>
)
