package cz.vvoleman.phr.common.presentation.model.healthcare.list

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class ListHealthcareViewState(
    val patient: PatientPresentationModel? = null,
)
