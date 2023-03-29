package cz.vvoleman.phr.common.presentation.model.listpatients

sealed class ListPatientsNotification {

    data class PatientAdded(val id: String) : ListPatientsNotification()
    data class PatientEdited(val id: String) : ListPatientsNotification()
    data class PatientDeleted(val id: String) : ListPatientsNotification()
    data class PatientSelected(val id: String) : ListPatientsNotification()

}