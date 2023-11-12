package cz.vvoleman.phr.featureMedicine.ui.list.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.MedicineCatalogueFragment
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.MedicineCatalogueViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.TimelineViewModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class MedicineFragmentAdapter(
    private val timelineViewModel: TimelineViewModel,
    private val medicineCatalogueViewModel: MedicineCatalogueViewModel,
    private val parent: Fragment
) : FragmentStateAdapter(parent) {

    fun setNextSchedules(nextSchedules: List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>) {
        timelineViewModel.setItems(nextSchedules)
        notifyItemChanged(0)
    }

    fun setAllSchedules(allSchedules: List<GroupedItemsUiModel<MedicineScheduleUiModel>>) {
        medicineCatalogueViewModel.setItems(allSchedules)
        notifyItemChanged(1)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TimelineFragment.newInstance(timelineViewModel)
            1 -> MedicineCatalogueFragment.newInstance(medicineCatalogueViewModel)
            else -> {
                throw IllegalStateException("Unknown position $position")
            }
        }
    }

}