package cz.vvoleman.phr.common.presentation.model.addedit

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

sealed class AddEditPatientNotification {

    data class PatientSaved(val patient: PatientPresentationModel) : AddEditPatientNotification()
    object Error : AddEditPatientNotification()
}