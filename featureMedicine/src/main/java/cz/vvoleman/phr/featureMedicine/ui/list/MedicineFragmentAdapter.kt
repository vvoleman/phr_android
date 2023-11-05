package cz.vvoleman.phr.featureMedicine.ui.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.CatalogueFragment
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class MedicineFragmentAdapter(
    private val timelineInterface: TimelineFragment.TimelineInterface,
    private val catalogueInterface: CatalogueFragment.CatalogueInterface,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    private var nextSchedules: List<ScheduleItemWithDetailsUiModel> = emptyList()
    private var allSchedules: List<MedicineScheduleUiModel> = emptyList()

    fun setNextSchedules(nextSchedules: List<ScheduleItemWithDetailsUiModel>) {
        this.nextSchedules = nextSchedules
        notifyItemChanged(0)
    }

    fun setAllSchedules(allSchedules: List<MedicineScheduleUiModel>) {
        this.allSchedules = allSchedules
        notifyItemChanged(1)
    }

    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TimelineFragment(timelineInterface, nextSchedules)
            1 -> CatalogueFragment(catalogueInterface, allSchedules)
            else -> {
                throw IllegalStateException("Unknown position $position")
            }
        }
    }
}