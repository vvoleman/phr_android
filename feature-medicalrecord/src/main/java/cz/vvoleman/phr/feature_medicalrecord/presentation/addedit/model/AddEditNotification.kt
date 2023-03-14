package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

sealed class AddEditNotification {
    object MissingData : AddEditNotification()
}