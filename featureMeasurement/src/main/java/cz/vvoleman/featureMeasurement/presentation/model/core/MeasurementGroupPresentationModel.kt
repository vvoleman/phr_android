package cz.vvoleman.featureMeasurement.presentation.model.core

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class MeasurementGroupPresentationModel(
    val id: String,
    val name: String,
    val patient: PatientPresentationModel,
    val scheduleItems: List<MeasurementGroupScheduleItemPresentationModel>,
    val fields: List<MeasurementGroupField>,
    val entries: List<MeasurementGroupEntryPresentationModel>,
)