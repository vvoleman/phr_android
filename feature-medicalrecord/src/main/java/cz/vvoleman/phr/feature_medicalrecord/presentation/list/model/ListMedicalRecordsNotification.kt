package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

sealed class ListMedicalRecordsNotification {
    object Success : ListMedicalRecordsNotification()
    object NotFoundError : ListMedicalRecordsNotification()
    object NotImplemented : ListMedicalRecordsNotification()
    data class RecordDeleted(val id: String) : ListMedicalRecordsNotification()
}
