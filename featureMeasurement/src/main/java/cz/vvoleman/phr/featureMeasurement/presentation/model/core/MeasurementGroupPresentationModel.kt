package cz.vvoleman.phr.featureMeasurement.presentation.model.core

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class MeasurementGroupPresentationModel(
    val id: String,
    val name: String,
    val patient: PatientPresentationModel,
    val scheduleItems: List<MeasurementGroupScheduleItemPresentationModel>,
    val fields: List<MeasurementGroupFieldPresentation>,
    val entries: List<MeasurementGroupEntryPresentationModel>,
)
