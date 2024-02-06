package cz.vvoleman.phr.featureEvent.ui.mapper.list.destination

import android.content.Context
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventDestination
import cz.vvoleman.phr.featureEvent.ui.view.list.ListEventFragmentDirections

class ListEventDestinationUiMapper(
    private val context: Context,
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as ListEventDestination) {
            ListEventDestination.AddEvent -> {
                navigateToAddEvent(cz.vvoleman.phr.common_datasource.R.string.action_add)
            }
            is ListEventDestination.EditEvent -> {
                navigateToAddEvent(
                    cz.vvoleman.phr.common_datasource.R.string.action_add,
                    dest.eventId
                )
            }
        }
    }

    private fun navigateToAddEvent(actionRes: Int, eventId: String? = null) {
        val actionLabel = context.resources.getString(actionRes)
        val action = ListEventFragmentDirections.actionListEventFragmentToAddEditEventFragment(
            action = actionLabel,
            eventId = eventId
        )

        navManager.navigate(action)
    }

    companion object {
        private const val TAG = "ListEventDestinationUiMapper"
    }
}
