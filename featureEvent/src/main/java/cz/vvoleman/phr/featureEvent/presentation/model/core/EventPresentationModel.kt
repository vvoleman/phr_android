package cz.vvoleman.phr.featureEvent.presentation.model.core

import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.domain.model.core.ReminderOffset
import java.time.LocalDateTime

data class EventPresentationModel(
    val id: String,
    val name: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime?,
    val patient: PatientPresentationModel,
    val specificMedicalWorker: SpecificMedicalWorkerPresentationModel?,
    val description: String?,
    val reminders: List<ReminderOffset>
)
