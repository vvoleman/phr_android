package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.withLeadingZero
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentTimelineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.TimelineAdapter
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel
import java.time.LocalDateTime

class TimelineFragment(
    private val listener: TimelineInterface,
    private val schedules: List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>
) : Fragment(), GroupedItemsAdapter.GroupedItemsAdapterInterface<ScheduleItemWithDetailsUiModel>,
    TimelineAdapter.TimelineAdapterInterface {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!

    private var isMultipleDays = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (schedules.isEmpty()) {
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            return
        }

        isMultipleDays = isMultipleDays()
        Log.d("TimelineFragment", "isMultipleDays: $isMultipleDays")

        val groupAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        groupAdapter.submitList(schedules)

        Log.d("TimelineFragment", "onViewCreated: ${schedules.size}")
    }

    override fun bind(binding: ItemGroupedItemsBinding, item: GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>) {
        val timelineAdapter = TimelineAdapter(this)
        Log.d("TimelineFragment", "bind: ${item.items.size}")

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
        listener.onTimelineItemClick(item)
    }

    override fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean) {
        listener.onTimelineItemAlarmToggle(item, oldState)
    }

    private fun isMultipleDays(): Boolean {
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
        Log.d("TimelineFragment", "getDateFromValue: $value")
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

}