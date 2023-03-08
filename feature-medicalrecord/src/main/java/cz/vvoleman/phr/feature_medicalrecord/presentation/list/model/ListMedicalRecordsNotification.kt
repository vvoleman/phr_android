package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

sealed class ListMedicalRecordsNotification {
    data class Error(val message: String) : ListMedicalRecordsNotification()
}