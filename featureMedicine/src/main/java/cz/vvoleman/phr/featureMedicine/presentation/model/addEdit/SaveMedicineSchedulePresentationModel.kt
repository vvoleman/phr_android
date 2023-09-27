package cz.vvoleman.phr.featureMedicine.presentation.model.addEdit

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel
import java.time.LocalDateTime

data class SaveMedicineSchedulePresentationModel(
    val id: String? = null,
    val patient: PatientPresentationModel,
    val medicine: MedicineDomainModel,
    val schedules: List<SaveScheduleItemDomainModel> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)
