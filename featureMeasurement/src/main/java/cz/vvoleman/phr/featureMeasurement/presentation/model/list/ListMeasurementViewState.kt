package cz.vvoleman.phr.featureMeasurement.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class ListMeasurementViewState(
    val patient: PatientPresentationModel,
    val nextSchedules: List<NextSchedulePresentationModel> = emptyList(),
    val selectedNextSchedule: NextSchedulePresentationModel? = null,
)
