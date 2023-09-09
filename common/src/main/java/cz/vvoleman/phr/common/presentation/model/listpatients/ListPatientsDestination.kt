package cz.vvoleman.phr.common.presentation.model.listpatients

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListPatientsDestination : PresentationDestination {

    object AddPatient : ListPatientsDestination()
    data class EditPatient(val id: String) : ListPatientsDestination()
    data class PatientSelected(val id: String) : ListPatientsDestination()
}
