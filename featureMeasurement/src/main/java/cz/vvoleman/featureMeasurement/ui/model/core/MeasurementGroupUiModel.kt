package cz.vvoleman.featureMeasurement.ui.model.core

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel

data class MeasurementGroupUiModel(
    val id: String,
    val name: String,
    val patient: PatientUiModel,
    val scheduleItems: List<MeasurementGroupScheduleItemUiModel>,
    val fields: List<MeasurementGroupField>,
    val entries: List<MeasurementGroupEntryUiModel>,
)
