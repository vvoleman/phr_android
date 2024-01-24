package cz.vvoleman.phr.featureMeasurement.ui.adapter.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.MeasurementGroupFragment
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.MeasurementTimelineFragment
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementGroupViewModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.TimelineViewModel

class MeasurementFragmentAdapter(
    private val measurementGroupViewModel: MeasurementGroupViewModel,
    private val timelineViewModel: TimelineViewModel,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    fun setAllGroups(allGroups: List<GroupedItemsUiModel<MeasurementGroupUiModel>>) {
        measurementGroupViewModel.setItems(allGroups)
        notifyItemChanged(0)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MeasurementGroupFragment.newInstance(measurementGroupViewModel)
            1 -> MeasurementTimelineFragment.newInstance(timelineViewModel)
            else -> {
                error("Invalid fragment adapter position")
            }
        }
    }
}
