package cz.vvoleman.phr.featureEvent.presentation.model.addEdit

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel

data class AddEditEventViewState(
    val patient: PatientPresentationModel,
    val event: EventPresentationModel? = null
)
