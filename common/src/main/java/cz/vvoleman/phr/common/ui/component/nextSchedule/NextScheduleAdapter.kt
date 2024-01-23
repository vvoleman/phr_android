package cz.vvoleman.phr.common.ui.component.nextSchedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common_datasource.databinding.ItemNextScheduleItemBinding

class NextScheduleAdapter :
    ListAdapter<NextScheduleItemUiModel, NextScheduleAdapter.NextScheduleViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextScheduleViewHolder {
        val binding =
            ItemNextScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NextScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NextScheduleViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class NextScheduleViewHolder(val binding: ItemNextScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NextScheduleItemUiModel) {
            binding.apply {
                textViewTime.text = item.time.toLocalTime().toString()
                textViewName.text = item.name
                textViewAdditionalInfo.text = item.additionalInfo
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NextScheduleItemUiModel>() {
        override fun areItemsTheSame(oldItem: NextScheduleItemUiModel, newItem: NextScheduleItemUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NextScheduleItemUiModel, newItem: NextScheduleItemUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
