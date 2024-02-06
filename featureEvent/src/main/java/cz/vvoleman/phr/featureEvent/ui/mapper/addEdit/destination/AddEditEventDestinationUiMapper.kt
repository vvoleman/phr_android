package cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.destination

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventDestination

class AddEditEventDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditEventDestination) {
            else -> {
                Log.e("AddEditEventDestinationUiMapper", "Unknown destination: $dest")
            }
        }
    }
}
