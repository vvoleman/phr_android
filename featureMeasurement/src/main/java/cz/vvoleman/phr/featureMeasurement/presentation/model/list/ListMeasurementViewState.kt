package cz.vvoleman.phr.featureMeasurement.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

data class ListMeasurementViewState(
    val patient: PatientPresentationModel,
    val nextSchedules: List<MeasurementGroupPresentationModel> = emptyList(),
    val selectedNextSchedule: MeasurementGroupPresentationModel? = null,
)
