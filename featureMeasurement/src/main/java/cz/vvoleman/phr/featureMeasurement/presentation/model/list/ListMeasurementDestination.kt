package cz.vvoleman.phr.featureMeasurement.presentation.model.list

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListMeasurementDestination : PresentationDestination {

    object AddMeasurementGroup : ListMeasurementDestination()

}
