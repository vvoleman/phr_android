package cz.vvoleman.phr.featureMedicine.presentation.model.addEdit

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemPresentationModel
import java.time.LocalDateTime

data class SaveMedicineSchedulePresentationModel(
    val id: String? = null,
    val patient: PatientPresentationModel,
    val medicine: MedicinePresentationModel,
    val schedules: List<ScheduleItemPresentationModel> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)
