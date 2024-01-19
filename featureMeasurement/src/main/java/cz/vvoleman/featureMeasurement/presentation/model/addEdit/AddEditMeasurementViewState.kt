package cz.vvoleman.featureMeasurement.presentation.model.addEdit

import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import java.time.LocalTime

data class AddEditMeasurementViewState(
    val patient: PatientPresentationModel,
    val measurementGroup: MeasurementGroupPresentationModel? = null,
    val times: Set<LocalTime> = emptySet(),
    val frequencyDay: List<FrequencyDayPresentationModel> = emptyList(),
    val frequencyDaysDefault: List<FrequencyDayPresentationModel> = emptyList(),
)
