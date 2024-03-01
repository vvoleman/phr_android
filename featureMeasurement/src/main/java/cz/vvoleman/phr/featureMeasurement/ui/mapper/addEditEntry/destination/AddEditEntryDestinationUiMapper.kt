package cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.NavigationSource
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.AddEditEntryFragmentDirections

class AddEditEntryDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditEntryDestination) {
            is AddEditEntryDestination.EntrySaved -> {
                handleEntrySaved(dest)
            }
        }
    }

    private fun handleEntrySaved(destination: AddEditEntryDestination.EntrySaved) {
         val action = when (destination.source) {
            NavigationSource.List -> {
                AddEditEntryFragmentDirections.actionAddEditEntryFragmentToListMeasurementFragment()
            }
            is NavigationSource.Detail -> {
                AddEditEntryFragmentDirections.actionAddEditEntryFragmentToDetailMeasurementGroupFragment(
                    measurementGroupId = destination.measurementGroupId,
                    name = destination.source.name
                )
            }
        }

        navManager.navigate(action)
    }
}
