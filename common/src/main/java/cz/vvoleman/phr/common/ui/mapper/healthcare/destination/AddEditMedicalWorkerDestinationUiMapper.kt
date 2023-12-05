package cz.vvoleman.phr.common.ui.mapper.healthcare.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper

class AddEditMedicalWorkerDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            else -> throw IllegalArgumentException("Unknown destination: $destination")
        }
    }
}
