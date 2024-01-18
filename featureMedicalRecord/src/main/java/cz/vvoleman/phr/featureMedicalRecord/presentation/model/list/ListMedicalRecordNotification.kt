package cz.vvoleman.phr.featureMedicalRecord.presentation.model.list

sealed class ListMedicalRecordNotification {
    object Success : ListMedicalRecordNotification()
    object NotFoundError : ListMedicalRecordNotification()
    object NotImplemented : ListMedicalRecordNotification()
    data class RecordDeleted(val id: String) : ListMedicalRecordNotification()
}
