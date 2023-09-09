package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder

sealed class AddEditBinderNotification {
    data class RecordOptionsItemClicked(val id: String) : AddEditBinderNotification()
    data class RecordClicked(val id: String) : AddEditBinderNotification()
}
