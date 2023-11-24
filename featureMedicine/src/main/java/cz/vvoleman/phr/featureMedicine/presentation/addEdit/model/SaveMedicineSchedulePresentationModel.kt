package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemPresentationModel
import java.time.LocalDateTime

data class SaveMedicineSchedulePresentationModel(
    val id: String? = null,
    val patient: PatientPresentationModel,
    val medicine: MedicinePresentationModel,
    val schedules: List<ScheduleItemPresentationModel> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)
