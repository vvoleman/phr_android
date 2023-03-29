package cz.vvoleman.phr.common.presentation.model.listpatients

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

data class ListPatientsViewState(
    val patients: List<PatientPresentationModel> = emptyList()
)
