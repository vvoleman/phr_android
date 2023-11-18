package cz.vvoleman.phr.featureMedicine.ui.component.frequencySelector

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.vvoleman.phr.featureMedicine.databinding.ItemFrequencyDayBinding
import java.time.format.TextStyle
import java.util.Locale

class FrequencyDayAdapter(
    private val listener: FrequencyDayListener
) : ListAdapter<FrequencyDayUiModel, FrequencyDayAdapter.FrequencyDayViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequencyDayViewHolder {
        val binding =
            ItemFrequencyDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FrequencyDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrequencyDayViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class FrequencyDayViewHolder(val binding: ItemFrequencyDayBinding) : ViewHolder(binding.root) {
        init {
            binding.apply {
                textViewDay.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            listener.onValueChange(item)
                        }
                    }
                }

                checkBoxDay.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            listener.onValueChange(item)
                        }
                    }
                }
            }
        }

        fun bind(day: FrequencyDayUiModel) {
            Log.d(TAG, "bind: ${day.day.getDisplayName(TextStyle.FULL, Locale.getDefault())}")
            binding.textViewDay.text = day.day.getDisplayName(TextStyle.FULL, Locale.getDefault())
            binding.checkBoxDay.isChecked = day.isSelected
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FrequencyDayUiModel>() {
        override fun areItemsTheSame(oldItem: FrequencyDayUiModel, newItem: FrequencyDayUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FrequencyDayUiModel, newItem: FrequencyDayUiModel): Boolean {
            return oldItem.day == newItem.day
        }
    }

    interface FrequencyDayListener {
        fun onValueChange(item: FrequencyDayUiModel)
    }

    companion object {
        private const val TAG = "FrequencyDayAdapter"
    }
}
