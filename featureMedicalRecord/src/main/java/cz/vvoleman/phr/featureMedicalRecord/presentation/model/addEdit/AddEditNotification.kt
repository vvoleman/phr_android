package cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit

sealed class AddEditNotification {
    object Success : AddEditNotification()
    object Error : AddEditNotification()
    object MissingData : AddEditNotification()
    object LimitFilesReached : AddEditNotification()
    object PatientNotSelected : AddEditNotification()
}
