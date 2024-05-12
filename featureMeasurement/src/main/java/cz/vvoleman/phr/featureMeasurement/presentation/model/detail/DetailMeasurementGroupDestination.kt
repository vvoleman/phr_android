package cz.vvoleman.phr.featureMeasurement.presentation.model.detail

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class DetailMeasurementGroupDestination : PresentationDestination {

    data class AddEntry(
        val measurementGroupId: String,
        val name: String
    ) : DetailMeasurementGroupDestination()

    data class EditEntry(
        val measurementGroupId: String,
        val entryId: String,
        val name: String,
    ) : DetailMeasurementGroupDestination()
}
