package cz.vvoleman.phr.featureEvent.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import java.time.LocalDate

data class ListEventViewState(
    val patient: PatientPresentationModel,
    val events: Map<LocalDate, List<EventPresentationModel>>
)
