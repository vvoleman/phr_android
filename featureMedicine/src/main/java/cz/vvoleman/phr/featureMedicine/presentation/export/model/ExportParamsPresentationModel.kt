package cz.vvoleman.phr.featureMedicine.presentation.export.model

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import java.time.LocalDateTime

data class ExportParamsPresentationModel(
    val data: List<ExportMedicineSchedulePresentationModel>,
    val patient: PatientPresentationModel,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)
