package cz.vvoleman.phr.featureMedicine.ui.export.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.getLocalString
import cz.vvoleman.phr.featureMedicine.databinding.ItemDocumentPageMedicineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel

class MedicinePageAdapter : ListAdapter<MedicineScheduleUiModel, MedicinePageAdapter.MedicineViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemDocumentPageMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MedicineViewHolder(private val binding: ItemDocumentPageMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MedicineScheduleUiModel) {
            val days = item.getDays().joinToString(", ") { it.getLocalString() }
            val times = item.getTimes().joinToString(", ")
            binding.apply {
                textViewName.text = item.medicine.name
                textViewFrequency.text = days
                textViewTimes.text = times
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MedicineScheduleUiModel>() {
        override fun areItemsTheSame(oldItem: MedicineScheduleUiModel, newItem: MedicineScheduleUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MedicineScheduleUiModel, newItem: MedicineScheduleUiModel): Boolean {
            return oldItem == newItem
        }
    }

}
