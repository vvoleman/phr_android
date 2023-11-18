package cz.vvoleman.phr.featureMedicine.ui.export.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.vvoleman.phr.common.utils.getLocalString
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemExportPreviewBinding
import cz.vvoleman.phr.featureMedicine.ui.export.model.ExportUiModel
import java.time.format.TextStyle

class ExportAdapter : ListAdapter<ExportUiModel, ExportAdapter.ExportViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExportViewHolder {
        val binding = ItemExportPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ExportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExportViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ExportViewHolder(private val binding: ItemExportPreviewBinding) : ViewHolder(binding.root) {

        fun bind(item: ExportUiModel) {
            val weekday = item.dateTime.dayOfWeek.getLocalString(TextStyle.SHORT)
            val time = item.dateTime.toLocalTime().toString()
            val context = binding.root.context
            binding.apply {
                textViewDate.text = item.dateTime.toLocalDate().toLocalString()
                textViewWeekdayTime.text = context.getString(R.string.export_date_format, weekday, time)
                textViewMedicine.text = item.medicineName
                textViewDosage.text = context.getString(R.string.dosage_format, item.quantity, item.unit)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ExportUiModel>() {
        override fun areItemsTheSame(oldItem: ExportUiModel, newItem: ExportUiModel): Boolean {
            return oldItem.getId() == newItem.getId()
        }

        override fun areContentsTheSame(oldItem: ExportUiModel, newItem: ExportUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
