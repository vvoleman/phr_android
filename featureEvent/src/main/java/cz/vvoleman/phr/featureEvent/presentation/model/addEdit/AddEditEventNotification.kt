package cz.vvoleman.phr.featureEvent.presentation.model.addEdit

sealed class AddEditEventNotification {
    data class MissingFields(val fields: List<AddEditEventViewState.RequiredField>) : AddEditEventNotification()
    object CannotSave : AddEditEventNotification()
}
