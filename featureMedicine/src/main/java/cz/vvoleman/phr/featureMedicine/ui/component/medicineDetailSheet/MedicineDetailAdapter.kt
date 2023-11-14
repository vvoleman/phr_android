package cz.vvoleman.phr.featureMedicine.ui.component.medicineDetailSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.databinding.ItemMedicineInfoBoxBinding

class MedicineDetailAdapter : ListAdapter<MedicineInfoUiModel, MedicineDetailAdapter.MedicineDetailViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineDetailViewHolder {
        val binding = ItemMedicineInfoBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicineDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineDetailViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicineDetailViewHolder(private val binding: ItemMedicineInfoBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(item: MedicineInfoUiModel) {
            val label = if (item.labelId.toIntOrNull() != null) {
                binding.root.context.getString(item.labelId.toInt())
            } else {
                item.labelId
            }

            binding.apply {
                textViewLabel.text = label
                textViewValue.text = item.value
            }
        }

    }

    private class DiffCallback() : DiffUtil.ItemCallback<MedicineInfoUiModel>() {
        override fun areItemsTheSame(oldItem: MedicineInfoUiModel, newItem: MedicineInfoUiModel) =
            oldItem.labelId == newItem.labelId

        override fun areContentsTheSame(oldItem: MedicineInfoUiModel, newItem: MedicineInfoUiModel) =
            oldItem == newItem
    }

}