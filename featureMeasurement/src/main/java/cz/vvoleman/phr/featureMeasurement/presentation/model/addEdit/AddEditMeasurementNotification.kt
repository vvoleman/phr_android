package cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit

import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupFieldUi

sealed class AddEditMeasurementNotification {

    data class EditItem(val item: MeasurementGroupFieldUi) : AddEditMeasurementNotification()
    data class MissingFields(val fields: List<AddEditMeasurementViewState.RequiredField>) :
        AddEditMeasurementNotification()
    object CannotSave : AddEditMeasurementNotification()
    object CannotSchedule : AddEditMeasurementNotification()
}
