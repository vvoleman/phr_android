package cz.vvoleman.phr.featureEvent.domain.model.addEdit

import cz.vvoleman.phr.featureEvent.domain.model.core.ReminderOffset
import java.time.LocalDateTime

data class SaveEventDomainModel(
    val id: String?,
    val name: String,
    val description: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime?,
    val patientId: String,
    val specificMedicalWorkerId: String?,
    val reminders: List<ReminderOffset>
)
