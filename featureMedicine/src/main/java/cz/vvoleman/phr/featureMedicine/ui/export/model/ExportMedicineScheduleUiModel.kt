package cz.vvoleman.phr.featureMedicine.ui.export.model

import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel

data class ExportMedicineScheduleUiModel(
    val id: String,
    val medicine: MedicineUiModel,
    val patient: PatientUiModel,
    val schedules: List<ExportScheduleItemUiModel>,
)
