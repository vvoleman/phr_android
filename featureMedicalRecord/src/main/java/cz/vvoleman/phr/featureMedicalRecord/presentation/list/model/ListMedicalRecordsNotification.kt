package cz.vvoleman.phr.featureMedicalRecord.presentation.list.model

sealed class ListMedicalRecordsNotification {
    object Success : ListMedicalRecordsNotification()
    object NotFoundError : ListMedicalRecordsNotification()
    object NotImplemented : ListMedicalRecordsNotification()
    data class RecordDeleted(val id: String) : ListMedicalRecordsNotification()
}
