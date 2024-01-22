package cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditMeasurementDestination : PresentationDestination {
    data class SaveSuccess(val id: String) : AddEditMeasurementDestination()
}
