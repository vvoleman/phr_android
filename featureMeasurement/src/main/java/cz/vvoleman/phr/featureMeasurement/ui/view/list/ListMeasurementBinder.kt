package cz.vvoleman.phr.featureMeasurement.ui.view.list

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.tabs.TabLayoutMediator
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.ui.adapter.list.MeasurementFragmentAdapter
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.ScheduledMeasurementGroupUiModelToPresentationMapper

class ListMeasurementBinder(
    private val nextScheduleMapper: NextScheduleUiModelToPresentationMapper,
    private val measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper,
    private val scheduledMapper: ScheduledMeasurementGroupUiModelToPresentationMapper
) :
    BaseViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding, ListMeasurementNotification>() {

    private var fragmentAdapter: MeasurementFragmentAdapter? = null
    private var isAdapterSet = false

    override fun init(
        viewBinding: FragmentListMeasurementBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
    }

    override fun bind(viewBinding: FragmentListMeasurementBinding, viewState: ListMeasurementViewState) {
        super.bind(viewBinding, viewState)

        if (fragmentAdapter != null && !isAdapterSet) {
            bindFragmentAdapter(viewBinding)
        }

        if (viewState.selectedNextSchedule?.dateTime != viewBinding.nextSchedule.getSchedule()?.dateTime) {
            val nextScheduleUi = viewState.selectedNextSchedule?.let { nextScheduleMapper.toUi(it) }
            viewBinding.nextSchedule.setSchedule(nextScheduleUi)
        }

        fragmentAdapter?.let { adapter ->
            val allGroups = viewState.groupedMeasurementGroups.map { group ->
                GroupedItemsUiModel(group.value, group.items.map { measurementGroupMapper.toUi(it) })
            }
            adapter.setAllGroups(allGroups)

            val scheduled = viewState.timelineSchedules.map { schedule ->
                GroupedItemsUiModel(schedule.value, schedule.items.map { scheduledMapper.toUi(it) })
            }
            adapter.setScheduledGroups(scheduled)
        }
    }

    fun setFragmentAdapter(fragmentAdapter: MeasurementFragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter
    }

    private fun bindFragmentAdapter(viewBinding: FragmentListMeasurementBinding) {
        viewBinding.viewPager.adapter = fragmentAdapter
        val textIds = listOf(R.string.fragment_timeline,R.string.fragment_measurement_group)

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = fragmentContext.getText(textIds[position])
        }.attach()

        isAdapterSet = true
    }

    override fun onDestroy(viewBinding: FragmentListMeasurementBinding) {
        super.onDestroy(viewBinding)

        fragmentAdapter = null
        isAdapterSet = false
    }
}
