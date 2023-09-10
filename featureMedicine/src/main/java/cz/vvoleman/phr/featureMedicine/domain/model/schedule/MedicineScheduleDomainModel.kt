package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import java.time.LocalDateTime

data class MedicineScheduleDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val medicine: MedicineDomainModel,
    val schedules: List<ScheduleItemDomainModel>,
    val createdAt: LocalDateTime
)
