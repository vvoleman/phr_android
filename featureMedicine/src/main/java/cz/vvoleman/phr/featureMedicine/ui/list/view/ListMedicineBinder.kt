package cz.vvoleman.phr.featureMedicine.ui.list.view

import com.google.android.material.tabs.TabLayoutMediator
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.list.MedicineFragmentAdapter
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.NextScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.ScheduleItemWithDetailsUiModelToPresentationMapper

@Suppress("UnusedPrivateProperty")
class ListMedicineBinder(
    private val nextScheduleItemMapper: NextScheduleItemUiModelToPresentationMapper,
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val scheduleItemMapper: ScheduleItemWithDetailsUiModelToPresentationMapper,
) : BaseViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding, ListMedicineBinder.Notification>() {

    private var fragmentAdapter: MedicineFragmentAdapter? = null

    private var isAdapterSet = false

    override fun bind(viewBinding: FragmentListMedicineBinding, viewState: ListMedicineViewState) {
        if (fragmentAdapter != null && !isAdapterSet) {
            bindFragmentAdapter(viewBinding)
        }

        if (viewState.selectedNextSchedule?.dateTime != viewBinding.nextSchedule.getSchedule()?.dateTime) {
            val nextScheduleUi = viewState.selectedNextSchedule?.let { nextScheduleItemMapper.toUi(it) }
            viewBinding.nextSchedule.setSchedule(nextScheduleUi)
        }

        fragmentAdapter?.let { adapter ->
            adapter.setNextSchedules(viewState.nextSchedules.map { scheduleItemMapper.toUi(it) })
        }

    }

    fun setFragmentAdapter(fragmentAdapter: MedicineFragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter
    }

    private fun bindFragmentAdapter(viewBinding: FragmentListMedicineBinding) {
        viewBinding.viewPager.adapter = fragmentAdapter
        val textIds = listOf(R.string.fragment_timeline, R.string.fragment_catalogue)

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = fragmentContext.getText(textIds[position])
        }.attach()
    }

    sealed class Notification

    companion object {
        const val TAG = "ListMedicineBinder"
    }
}
