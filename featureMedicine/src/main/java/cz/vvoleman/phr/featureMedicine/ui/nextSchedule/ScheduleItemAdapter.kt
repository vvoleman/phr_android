package cz.vvoleman.phr.featureMedicine.ui.nextSchedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemNextScheduleItemBinding

class ScheduleItemAdapter :
    ListAdapter<NextScheduleUiModel, ScheduleItemAdapter.ScheduleItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val binding =
            ItemNextScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ScheduleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ScheduleItemViewHolder(val binding: ItemNextScheduleItemBinding) : ViewHolder(binding.root) {
        init {

        }

        fun bind(item: NextScheduleUiModel) {
            binding.apply {
                textViewTime.text = item.time.toLocalTime().toString()
                textViewName.text = item.medicineName
                textViewDosage.text =
                    binding.root.context.getString(R.string.schedule_item_dosage, item.quantity, item.unit)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NextScheduleUiModel>() {
        override fun areItemsTheSame(oldItem: NextScheduleUiModel, newItem: NextScheduleUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NextScheduleUiModel, newItem: NextScheduleUiModel): Boolean {
            return oldItem == newItem
        }
    }

}