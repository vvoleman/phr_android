package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditEntryDestination : PresentationDestination {
    data class EntrySaved(val measurementGroupId: String, val source: NavigationSource) : AddEditEntryDestination()
}
