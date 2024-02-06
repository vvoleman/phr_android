package cz.vvoleman.phr.featureEvent.data.model.core

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import java.time.LocalDateTime

data class EventDataModel(
    val id: String,
    val name: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime?,
    val patient: PatientDomainModel,
    val specificMedicalWorker: SpecificMedicalWorkerDomainModel?,
    val description: String?,
    val reminders: List<Long>
)
