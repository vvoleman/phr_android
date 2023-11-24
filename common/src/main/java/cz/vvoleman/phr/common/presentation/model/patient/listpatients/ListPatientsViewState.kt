package cz.vvoleman.phr.common.presentation.model.patient.listpatients

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class ListPatientsViewState(
    val patients: List<PatientPresentationModel> = emptyList(),
    val selectedPatientId: String? = null
)
