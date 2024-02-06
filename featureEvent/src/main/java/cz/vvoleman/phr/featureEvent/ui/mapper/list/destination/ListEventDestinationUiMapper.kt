package cz.vvoleman.phr.featureEvent.ui.mapper.list.destination

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper

class ListEventDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            else -> {
                Log.e(TAG, "navigate: Unknown destination")
            }
        }
    }

    companion object {
        private const val TAG = "ListEventDestinationUiMapper"
    }
}
