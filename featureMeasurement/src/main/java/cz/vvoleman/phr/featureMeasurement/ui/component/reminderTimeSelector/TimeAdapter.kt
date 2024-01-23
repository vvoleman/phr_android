package cz.vvoleman.phr.featureMeasurement.ui.component.reminderTimeSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMeasurement.databinding.ItemReminderTimeSelectorBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder

class TimeAdapter(
    private val listener: TimeAdapterListener
) : ListAdapter<LocalTime, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemReminderTimeSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class TimeViewHolder(private val binding: ItemReminderTimeSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                textViewTime.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            listener.onTimeClick(position, textViewTime)
                        }
                    }
                }
            }
        }

        fun bind(time: LocalTime) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("HH:mm")
                .toFormatter()
            binding.textViewTime.text = time.format(formatter)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LocalTime>() {
        override fun areItemsTheSame(oldItem: LocalTime, newItem: LocalTime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocalTime, newItem: LocalTime): Boolean {
            return oldItem == newItem
        }
    }

    interface TimeAdapterListener {
        fun onTimeClick(index: Int, anchorView: View)
    }
}
