package cz.vvoleman.phr.common.presentation.model.patient.addedit

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

sealed class AddEditPatientNotification {

    data class PatientSaved(val patient: PatientPresentationModel) : AddEditPatientNotification()
    object Error : AddEditPatientNotification()
}
