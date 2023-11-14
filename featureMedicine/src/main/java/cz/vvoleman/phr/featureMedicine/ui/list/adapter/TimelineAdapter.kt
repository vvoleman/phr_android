package cz.vvoleman.phr.featureMedicine.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemTimelineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel

class TimelineAdapter (
    private val listener: TimelineAdapterInterface
) :
    ListAdapter<ScheduleItemWithDetailsUiModel, TimelineAdapter.TimelineViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val binding = ItemTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(item: ScheduleItemWithDetailsUiModel) {
            binding.apply {
                textViewName.text = item.medicine.name
                layoutInfo.textViewTime.text = item.scheduleItem.time.toString()
                layoutInfo.textViewDosage.text = binding.root.context.getString(
                    R.string.schedule_item_dosage,
                    item.scheduleItem.quantity,
                    item.scheduleItem.unit
                )

                val alarmIcon = if (item.isAlarmEnabled) {
                    cz.vvoleman.phr.common_datasource.R.drawable.ic_alarm_on
                } else {
                    cz.vvoleman.phr.common_datasource.R.drawable.ic_alarm_off
                }

                // Change drawable
                buttonAlarm.setCompoundDrawablesWithIntrinsicBounds(alarmIcon, 0, 0, 0)

            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<ScheduleItemWithDetailsUiModel>() {
        override fun areItemsTheSame(oldItem: ScheduleItemWithDetailsUiModel, newItem: ScheduleItemWithDetailsUiModel) =
            oldItem.scheduleItem.id == newItem.scheduleItem.id

        override fun areContentsTheSame(
            oldItem: ScheduleItemWithDetailsUiModel,
            newItem: ScheduleItemWithDetailsUiModel
        ) =
            oldItem == newItem
    }

    interface TimelineAdapterInterface {
        fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean)
    }

}