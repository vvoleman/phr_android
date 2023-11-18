package cz.vvoleman.phr.featureMedicine.presentation.export.model

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel

data class ExportMedicineSchedulePresentationModel(
    val id: String,
    val medicine: MedicinePresentationModel,
    val patient: PatientPresentationModel,
    val schedules: List<ExportScheduleItemPresentationModel>,
)
