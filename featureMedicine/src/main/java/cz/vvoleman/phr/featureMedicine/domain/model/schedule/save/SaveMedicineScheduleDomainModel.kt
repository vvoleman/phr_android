package cz.vvoleman.phr.featureMedicine.domain.model.schedule.save

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import java.time.LocalDateTime

data class SaveMedicineScheduleDomainModel(
    val id: String? = null,
    val patient: PatientDomainModel,
    val medicine: MedicineDomainModel,
    val schedules: List<ScheduleItemDomainModel> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)
