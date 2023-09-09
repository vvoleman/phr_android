package cz.vvoleman.phr.feature_medicine.ui.time_selector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.feature_medicine.databinding.ItemTimeSelectorBinding
import java.time.format.DateTimeFormatterBuilder

class TimeAdapter : ListAdapter<TimeUiModel, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemTimeSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class TimeViewHolder(val binding: ItemTimeSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(time: TimeUiModel) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("HH:mm")
                .toFormatter()
            binding.textViewTime.text = time.time.format(formatter)
            binding.editTextAmount.setText(time.number.toString())
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TimeUiModel>() {
        override fun areItemsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
