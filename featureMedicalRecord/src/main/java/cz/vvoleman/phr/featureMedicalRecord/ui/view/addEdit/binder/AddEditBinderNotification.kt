package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder

sealed class AddEditBinderNotification {
    data class RecordOptionsItemClicked(val id: String) : AddEditBinderNotification()
    data class RecordClicked(val id: String) : AddEditBinderNotification()
}
