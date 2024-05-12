package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.fragment.AbstractTimelineFragment
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.checkVisibility
import cz.vvoleman.phr.common.utils.withLeadingZero
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentTimelineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.TimelineAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.TimelineViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel

class TimelineFragment :
    AbstractTimelineFragment<ScheduleItemWithDetailsUiModel>(),
    TimelineAdapter.TimelineAdapterInterface {

    private var viewModel: TimelineViewModel? = null

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!

    private var isMultipleDays = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireParentFragment())[TimelineViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel?.getListener() == null) {
            Log.e(TAG, "onViewCreated: listener is null")
            return
        }

        val groupAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        Log.d(TAG, "onViewCreated: aha")
        collectLatestLifecycleFlow(viewModel!!.items) {
            binding.textViewEmpty.visibility = checkVisibility(it is UiState.Success && it.data.isEmpty())
            binding.recyclerView.visibility = checkVisibility(it is UiState.Success && it.data.isNotEmpty())
            binding.progressBar.visibility = checkVisibility(it is UiState.Loading)

            Log.d(TAG, "state: $it")
            if (it is UiState.Success) {
                Log.d(TAG, "onViewCreated: ${it.data}")
                groupAdapter.submitList(it.data)
            }
        }
    }

    override fun bindGroupedItems(
        groupBinding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>
    ) {
        val timelineAdapter = TimelineAdapter(this)

        val dateTime = getDateFromValue(item.value.toString())
        var text = if (isMultipleDays) {
            "${dateTime.dayOfMonth.withLeadingZero()}. ${dateTime.monthValue.withLeadingZero()} "
        } else {
            ""
        }
        text += "${dateTime.hour.withLeadingZero()}:${dateTime.minute.withLeadingZero()}"
        groupBinding.apply {
            textViewTitle.text = text
            recyclerView.apply {
                adapter = timelineAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        timelineAdapter.submitList(item.items.toList())
    }

    override fun onDestroyGroupedItems(groupBinding: ItemGroupedItemsBinding) {
        groupBinding.recyclerView.adapter = null
    }

    override fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel) {
        viewModel?.getListener()?.onTimelineItemClick(item)
    }

    override fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean) {
        viewModel?.getListener()?.onTimelineItemAlarmToggle(item, oldState)
    }

    fun setViewModel(viewModel: TimelineViewModel) {
        this.viewModel = viewModel
    }

    interface TimelineInterface {
        fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TAG = "TimelineFragment"

        fun newInstance(viewModel: TimelineViewModel): TimelineFragment {
            val fragment = TimelineFragment()
            fragment.setViewModel(viewModel)

            return fragment
        }
    }
}
