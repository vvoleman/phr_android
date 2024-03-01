package cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.destination

import android.content.Context
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.NavigationSource
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupDestination
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.DetailMeasurementGroupFragmentDirections

class DetailMeasurementGroupDestinationUiMapper(
    private val context: Context,
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as DetailMeasurementGroupDestination) {
            is DetailMeasurementGroupDestination.AddEntry -> {
                val actionLabel = context.resources.getString(cz.vvoleman.phr.common_datasource.R.string.action_add)

                val action = DetailMeasurementGroupFragmentDirections
                    .actionDetailMeasurementGroupFragmentToAddEditEntryFragment(
                        action = actionLabel,
                        measurementGroupId = dest.measurementGroupId,
                        source = NavigationSource.Detail(dest.measurementGroupId, dest.name)
                    )
                navManager.navigate(action)
            }
        }
    }
}
