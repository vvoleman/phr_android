package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

sealed class ListMedicalRecordsNotification {
    object Success : ListMedicalRecordsNotification()
    object NotFoundError : ListMedicalRecordsNotification()
}