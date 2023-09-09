package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

sealed class AddEditNotification {
    object Success : AddEditNotification()
    object Error : AddEditNotification()
    object MissingData : AddEditNotification()
    object LimitFilesReached : AddEditNotification()
    object PatientNotSelected : AddEditNotification()
}
