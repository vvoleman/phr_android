package cz.vvoleman.featureMeasurement.ui.model.core

import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel

data class MeasurementGroupUiModel(
    val id: String,
    val name: String,
    val patient: PatientUiModel,
    val scheduleItems: List<MeasurementGroupScheduleItemUiModel>,
    val fields: List<MeasurementGroupFieldUi>,
    val entries: List<MeasurementGroupEntryUiModel>,
)
