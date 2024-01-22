package cz.vvoleman.phr.featureMeasurement.ui.mapper.addEdit.destination

import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.ui.view.addEdit.AddEditMeasurementFragmentDirections
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper

class AddEditMeasurementDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditMeasurementDestination) {
            is AddEditMeasurementDestination.SaveSuccess -> {
                val action = AddEditMeasurementFragmentDirections
                    .actionAddEditMeasurementFragmentToListMeasurementFragment(savedMeasurementGroupId = dest.id)
                navManager.navigate(action)
            }
        }
    }
}
