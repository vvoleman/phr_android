package cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination

import android.content.Context
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.ui.view.list.ListMeasurementFragmentDirections
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper

class ListMeasurementDestinationUiMapper(
    private val context: Context,
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as ListMeasurementDestination) {
            ListMeasurementDestination.AddMeasurementGroup -> {
                navigateToAddEdit(cz.vvoleman.phr.common_datasource.R.string.action_add)
            }
        }
    }

    private fun navigateToAddEdit(actionId: Int) {
        val actionLabel = context.resources.getString(actionId)
        val action = ListMeasurementFragmentDirections
            .actionListMeasurementFragmentToAddEditMeasurementFragment(actionLabel)
        navManager.navigate(action)
    }
}
