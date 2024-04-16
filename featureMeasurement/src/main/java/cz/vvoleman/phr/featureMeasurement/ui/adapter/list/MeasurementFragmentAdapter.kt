package cz.vvoleman.phr.featureMeasurement.ui.adapter.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.MeasurementGroupFragment
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.MeasurementTimelineFragment
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementGroupViewModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementTimelineViewModel

class MeasurementFragmentAdapter(
    private val measurementGroupViewModel: MeasurementGroupViewModel,
    private val timelineViewModel: MeasurementTimelineViewModel,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    fun setAllGroups(allGroups: List<GroupedItemsUiModel<MeasurementGroupUiModel>>) {
        measurementGroupViewModel.setItems(allGroups)
        notifyItemChanged(0)
    }

    fun setScheduledGroups(scheduledGroups: List<GroupedItemsUiModel<ScheduledMeasurementGroupUiModel>>) {
        timelineViewModel.setItems(scheduledGroups)
        notifyItemChanged(1)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MeasurementTimelineFragment.newInstance(timelineViewModel)
            1 -> MeasurementGroupFragment.newInstance(measurementGroupViewModel)
            else -> {
                error("Invalid fragment adapter position")
            }
        }
    }
}
