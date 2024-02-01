package cz.vvoleman.phr.featureMeasurement.ui.component.fieldInfoTable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMeasurement.databinding.ItemFieldInfoTableBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.addEditEntry.EntryFieldUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel

class FieldInfoTableAdapter(
    private val listener: FieldInfoTableListener
) : ListAdapter<EntryInfoUiModel, FieldInfoTableAdapter.FieldInfoTableViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldInfoTableViewHolder {
        val binding = ItemFieldInfoTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FieldInfoTableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldInfoTableViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class FieldInfoTableViewHolder(private val binding: ItemFieldInfoTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                if (item != null) {
                    listener.onItemOptionsMenuClicked(item, it)
                }
            }
        }

        fun bind(item: EntryInfoUiModel) {
            val names = nameToValue(item)
            val valueString = names.map { "${it.key}: ${it.value}" }.joinToString(", ")

            binding.apply {
                textViewDatetime.text = item.entry.createdAt.toLocalString()
                textViewValue.text = valueString
            }
        }

        private fun nameToValue(item: EntryInfoUiModel): Map<String, String> {
            return item.fields
                .associate { it.id to it.name }
                .map { it.value to (item.entry.values[it.key]?: "") }
                .toMap()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<EntryInfoUiModel>() {
        override fun areItemsTheSame(oldItem: EntryInfoUiModel, newItem: EntryInfoUiModel): Boolean {
            return oldItem.entry.id == newItem.entry.id
        }

        override fun areContentsTheSame(oldItem: EntryInfoUiModel, newItem: EntryInfoUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface FieldInfoTableListener {
        fun onItemOptionsMenuClicked(item: EntryInfoUiModel, anchorView: View)
    }

}
