package cz.vvoleman.phr.featureMedicine.ui.list.view

import com.google.android.material.tabs.TabLayoutMediator
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineFragmentAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.NextScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemWithDetailsUiModelToPresentationMapper

@Suppress("UnusedPrivateProperty")
class ListMedicineBinder(
    private val nextScheduleItemMapper: NextScheduleItemUiModelToPresentationMapper,
    private val medicineScheduleMapper: MedicineScheduleUiModelToPresentationMapper,
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
            adapter.setNextSchedules(
                viewState.timelineSchedules.map { group ->
                    val scheduleItems = group.items.map { scheduleItemMapper.toUi(it) }
                    GroupedItemsUiModel(group.value, scheduleItems)
                }
            )
            adapter.setAllSchedules(
                viewState.medicineCatalogue.map { group ->
                    GroupedItemsUiModel(group.value, group.items.map { medicineScheduleMapper.toUi(it) })
                }
            )
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
