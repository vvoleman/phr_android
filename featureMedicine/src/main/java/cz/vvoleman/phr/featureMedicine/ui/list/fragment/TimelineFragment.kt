package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.withLeadingZero
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentTimelineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.TimelineAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.TimelineViewModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel
import java.time.LocalDateTime

class TimelineFragment(
) : Fragment(), GroupedItemsAdapter.GroupedItemsAdapterInterface<ScheduleItemWithDetailsUiModel>,
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

        Log.i(TAG, "onViewCreated, is model ready?: ${viewModel?.isReady}")
        if (viewModel?.isReady != true) {
            return
        }

        Log.i(TAG, "is it same viewmodel? ${viewModel?.test}")

        val schedules = viewModel!!.getItems()

        if (schedules.isEmpty()) {
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            return
        }

        isMultipleDays = isMultipleDays(schedules)

        val groupAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        groupAdapter.submitList(schedules)
    }

    override fun bind(binding: ItemGroupedItemsBinding, item: GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>) {
        val timelineAdapter = TimelineAdapter(this)

        val dateTime = getDateFromValue(item.value.toString())
        var text = if (isMultipleDays) {
            "${dateTime.dayOfMonth.withLeadingZero()}. ${dateTime.monthValue.withLeadingZero()} "
        } else {
            ""
        }
        text += "${dateTime.hour.withLeadingZero()}:${dateTime.minute.withLeadingZero()}"
        binding.apply {
            textViewTitle.text = text
            recyclerView.apply {
                adapter = timelineAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        timelineAdapter.submitList(item.items)
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

    private fun isMultipleDays(schedules: List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>): Boolean {
        if (schedules.size < 2) {
            return false
        }

        val keys = schedules.map {
            it.value.toString().split("-").take(3).joinToString("-")
        }.toMutableList()

        val firstDate = keys.removeFirst()

        return !keys.all { it == firstDate }
    }

    private fun getDateFromValue(value: String): LocalDateTime {
        val date = value.split("-")

        if (date.size != 4) {
            return LocalDateTime.now()
        }

        return LocalDateTime.of(date[0].toInt(), date[1].toInt(), date[2].toInt(), date[3].toInt(), 0)
    }

    interface TimelineInterface {
        fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean)
    }

    companion object {
        const val TAG = "TimelineFragment"
        const val ARG_SCHEDULES = "schedules"

        fun newInstance(viewModel: TimelineViewModel): TimelineFragment {
            val fragment = TimelineFragment()
            fragment.setViewModel(viewModel)

            return fragment
        }
    }

}