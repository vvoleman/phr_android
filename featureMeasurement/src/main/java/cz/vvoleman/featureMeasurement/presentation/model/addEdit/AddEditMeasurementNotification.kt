package cz.vvoleman.featureMeasurement.presentation.model.addEdit

import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupFieldUi

sealed class AddEditMeasurementNotification {

    data class EditItem(val item: MeasurementGroupFieldUi) : AddEditMeasurementNotification()
    data class MissingFields(val fields: List<AddEditMeasurementViewState.RequiredField>) :
        AddEditMeasurementNotification()
    object SaveError : AddEditMeasurementNotification()
}
