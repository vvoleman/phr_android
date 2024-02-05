package cz.vvoleman.phr.featureMedicine.presentation.export.model

import cz.vvoleman.phr.common.domain.model.export.ExportType
import cz.vvoleman.phr.common.presentation.model.export.PermissionStatus
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import java.time.LocalDateTime

data class ExportViewState(
    val patient: PatientPresentationModel? = null,
    val exportType: ExportType = ExportType.PDF,
    val medicine: List<MedicinePresentationModel> = emptyList(),
    val dateRange: Pair<LocalDateTime, LocalDateTime>,
    val exportData: List<ExportMedicineSchedulePresentationModel> = emptyList(),
    val permissionStatus: PermissionStatus? = null,
)
