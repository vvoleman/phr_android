package cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventDestination
import cz.vvoleman.phr.featureEvent.ui.view.addEdit.AddEditEventFragmentDirections

class AddEditEventDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditEventDestination) {
            is AddEditEventDestination.Saved -> {
                val action = AddEditEventFragmentDirections.actionAddEditEventFragmentToListEventFragment(
                    dest.id
                )

                navManager.navigate(action)
            }
        }
    }
}
