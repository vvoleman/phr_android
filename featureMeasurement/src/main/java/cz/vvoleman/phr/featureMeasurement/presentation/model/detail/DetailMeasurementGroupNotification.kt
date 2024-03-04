package cz.vvoleman.phr.featureMeasurement.presentation.model.detail

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

sealed class DetailMeasurementGroupNotification {
    data class Export(val measurementGroup: MeasurementGroupPresentationModel) : DetailMeasurementGroupNotification()
}
