package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

sealed class AddEditEntryNotification {
    data class FieldErrors(val errors: List<AddEditEntryViewState.FieldError>) : AddEditEntryNotification()
}
