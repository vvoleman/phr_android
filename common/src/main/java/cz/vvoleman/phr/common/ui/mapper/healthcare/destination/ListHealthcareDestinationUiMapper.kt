package cz.vvoleman.phr.common.ui.mapper.healthcare.destination

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareDestination

class ListHealthcareDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination as ListHealthcareDestination) {
            else -> Log.e("ListHealthcareDestinationUiMapper", "Unknown destination: $destination")
        }
    }
}
