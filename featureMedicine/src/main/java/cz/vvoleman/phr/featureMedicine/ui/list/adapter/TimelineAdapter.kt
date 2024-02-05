package cz.vvoleman.phr.featureMedicine.ui.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemTimelineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel

class TimelineAdapter(
    private val listener: TimelineAdapterInterface
) :
    ListAdapter<ScheduleItemWithDetailsUiModel, TimelineAdapter.TimelineViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

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
            binding.buttonAlarm.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                val oldState = item.isAlarmEnabled
                listener.onTimelineItemAlarmToggle(item, oldState)
            }
        }

        fun bind(item: ScheduleItemWithDetailsUiModel) {
            binding.apply {
                textViewName.text = item.medicine.name
                textViewInfoTime.text = item.scheduleItem.time.toString()
                textViewInfoAdditionalInfo.text = binding.root.context.getString(
                    R.string.schedule_item_dosage,
                    item.scheduleItem.quantity,
                    item.scheduleItem.unit
                )

                val alarmIcon = if (item.isAlarmEnabled) {
                    cz.vvoleman.phr.common_datasource.R.drawable.ic_alarm_on
                } else {
                    cz.vvoleman.phr.common_datasource.R.drawable.ic_alarm_off
                }

                buttonAlarm.icon = AppCompatResources.getDrawable(binding.root.context, alarmIcon)
                // Change drawable
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ScheduleItemWithDetailsUiModel>() {
        override fun areItemsTheSame(
            oldItem: ScheduleItemWithDetailsUiModel,
            newItem: ScheduleItemWithDetailsUiModel
        ): Boolean {
            Log.d("TimelineAdapter", "areItemsTheSame: ${oldItem.scheduleItem.id == newItem.scheduleItem.id}")
            return oldItem.scheduleItem.id == newItem.scheduleItem.id
        }

        override fun areContentsTheSame(
            oldItem: ScheduleItemWithDetailsUiModel,
            newItem: ScheduleItemWithDetailsUiModel
        ): Boolean {
            Log.d("TimelineAdapter", "areContentsTheSame: ${oldItem == newItem}")
            return oldItem == newItem
        }
    }

    interface TimelineAdapterInterface {
        fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean)
    }
}
