package cz.vvoleman.phr.featureMedicine.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemMedicineCatalogueBinding
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel
import java.time.format.TextStyle
import java.util.Locale

class MedicineCatalogueAdapter(
    private val listener: MedicineCatalogueAdapterInterface
) : ListAdapter<MedicineScheduleUiModel, MedicineCatalogueAdapter.MedicineCatalogueViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineCatalogueViewHolder {
        val binding = ItemMedicineCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicineCatalogueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineCatalogueViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicineCatalogueViewHolder(private val binding: ItemMedicineCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onCatalogueItemClick(item)
                    }
                }

                buttonEdit.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onCatalogueItemEdit(item)
                    }
                }
            }
        }

        fun bind(item: MedicineScheduleUiModel) {
            val times = item.getTimes().joinToString(SEPARATOR)
            val frequencies =
                item.getDays().map { it.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()) }
                    .let {
                        if (it.size == 7) {
                            binding.root.context.getString(R.string.frequency_everyday)
                        } else {
                            it.joinToString(SEPARATOR)
                        }
                    }


            binding.apply {
                textViewName.text = item.medicine.name
                textViewTimes.text = times
                textViewFrequency.text = frequencies
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MedicineScheduleUiModel>() {
        override fun areItemsTheSame(oldItem: MedicineScheduleUiModel, newItem: MedicineScheduleUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MedicineScheduleUiModel, newItem: MedicineScheduleUiModel) =
            oldItem == newItem
    }

    interface MedicineCatalogueAdapterInterface {
        fun onCatalogueItemClick(item: MedicineScheduleUiModel)

        fun onCatalogueItemEdit(item: MedicineScheduleUiModel)
    }

    companion object {
        private const val TAG = "MedicineCatalogueAdapter"
        private const val SEPARATOR = " | "
    }

}