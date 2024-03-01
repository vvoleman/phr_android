package cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination

import android.content.Context
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.NavigationSource
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.ui.view.list.ListMeasurementFragmentDirections

class ListMeasurementDestinationUiMapper(
    private val context: Context,
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as ListMeasurementDestination) {
            ListMeasurementDestination.AddMeasurementGroup -> {
                navigateToAddEdit(cz.vvoleman.phr.common_datasource.R.string.action_add)
            }
            is ListMeasurementDestination.EditMeasurementGroup -> {
                navigateToAddEdit(cz.vvoleman.phr.common_datasource.R.string.action_edit, dest.id)
            }
            is ListMeasurementDestination.AddEntry -> {
                val action = ListMeasurementFragmentDirections
                    .actionListMeasurementFragmentToAddEditEntryFragment(
                        action = context.resources.getString(cz.vvoleman.phr.common_datasource.R.string.action_add),
                        measurementGroupId = dest.measurementGroupId,
                        source = NavigationSource.List
                    )
                navManager.navigate(action)
            }
            is ListMeasurementDestination.Detail -> {
                val action = ListMeasurementFragmentDirections
                    .actionListMeasurementFragmentToDetailMeasurementGroupFragment(dest.measurementGroupId, dest.name)
                navManager.navigate(action)
            }
        }
    }

    private fun navigateToAddEdit(actionId: Int, id: String? = null) {
        val actionLabel = context.resources.getString(actionId)
        val action = ListMeasurementFragmentDirections
            .actionListMeasurementFragmentToAddEditMeasurementFragment(
                action = actionLabel,
                measurementGroupId = id
            )
        navManager.navigate(action)
    }
}
