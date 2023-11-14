package cz.vvoleman.phr.featureMedicine.ui.component.scheduleDetailDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.databinding.ItemScheduleDetailMedicineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel

class ScheduleDetailAdapter(
    private val listener: ScheduleDetailListener
) :
    ListAdapter<ScheduleItemWithDetailsUiModel, ScheduleDetailAdapter.ScheduleDetailViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDetailViewHolder {
        val binding = ItemScheduleDetailMedicineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ScheduleDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ScheduleDetailViewHolder(private val binding: ItemScheduleDetailMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleItemWithDetailsUiModel) {
            binding.textViewName.text = item.medicine.name

            binding.buttonLeaflet.setOnClickListener {
                listener.onLeafletOpen(item)
            }

            binding.textViewDosageValue.text = String.format(
                "%s tablety",
                item.scheduleItem.quantity,
                item.scheduleItem.unit
            )
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ScheduleItemWithDetailsUiModel>() {
        override fun areItemsTheSame(
            oldItem: ScheduleItemWithDetailsUiModel,
            newItem: ScheduleItemWithDetailsUiModel
        ): Boolean {
            return oldItem.scheduleItem.id == newItem.scheduleItem.id
        }

        override fun areContentsTheSame(
            oldItem: ScheduleItemWithDetailsUiModel,
            newItem: ScheduleItemWithDetailsUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ScheduleDetailListener {
        fun onLeafletOpen(scheduleItem: ScheduleItemWithDetailsUiModel)
    }

}
