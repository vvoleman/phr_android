package cz.vvoleman.phr.featureMedicine.ui.timeSelector

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.databinding.ItemTimeSelectorBinding
import java.time.format.DateTimeFormatterBuilder

class TimeAdapter(
    private val listener: TimeAdapterListener
) : ListAdapter<TimeUiModel, TimeAdapter.TimeViewHolder>(DiffCallback()) {

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

        fun bind(time: TimeUiModel) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("HH:mm")
                .toFormatter()
            binding.textViewTime.text = time.time.format(formatter)
            Log.d("TimeAdapter", "number value: ${time.number}")
            binding.numberPickerAmount.value = time.number.toInt()
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

    interface TimeAdapterListener {
        fun onTimeClick(index: Int, anchorView: View)
    }
}
