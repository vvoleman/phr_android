package cz.vvoleman.phr.common.presentation.model.patient.addedit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditPatientDestination : PresentationDestination {

    data class PatientSaved(val id: String) : AddEditPatientDestination()
    object Back : AddEditPatientDestination()
}
