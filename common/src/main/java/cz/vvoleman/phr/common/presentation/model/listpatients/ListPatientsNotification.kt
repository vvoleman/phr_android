package cz.vvoleman.phr.common.presentation.model.listpatients

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

sealed class ListPatientsNotification {

    data class PatientAdded(val id: String) : ListPatientsNotification()
    data class PatientEdited(val id: String) : ListPatientsNotification()
    data class PatientDeleted(val patient: PatientPresentationModel) : ListPatientsNotification()
    data class PatientDeleteFailed(val patient: PatientPresentationModel) : ListPatientsNotification()
    data class PatientSelected(val id: String) : ListPatientsNotification()
}
