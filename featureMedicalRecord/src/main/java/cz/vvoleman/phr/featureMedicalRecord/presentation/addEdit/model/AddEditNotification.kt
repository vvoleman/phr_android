package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

sealed class AddEditNotification {
    object Success : AddEditNotification()
    object Error : AddEditNotification()
    object MissingData : AddEditNotification()
    object LimitFilesReached : AddEditNotification()
    object PatientNotSelected : AddEditNotification()
}
