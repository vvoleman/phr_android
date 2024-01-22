package cz.vvoleman.featureMeasurement.ui.component.fieldEditor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.vvoleman.featureMeasurement.databinding.ItemFieldEditBinding
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.featureMeasurement.ui.model.core.field.NumericFieldUiModel

class FieldAdapter(
    private val listener: FieldAdapterListener
): ListAdapter<MeasurementGroupFieldUi, FieldAdapter.FieldViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding = ItemFieldEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    inner class FieldViewHolder(private val binding: ItemFieldEditBinding): ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onFieldClick(item, bindingAdapterPosition)
                }
            }
            binding.buttonOptions.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onFieldOptionsMenuClicked(item, it)
                }
            }
        }

        fun bind(item: MeasurementGroupFieldUi) {
            binding.apply {
                textViewName.text = item.name
                textViewType.text = getTypeName(item)
            }
        }

        private fun getTypeName(item: MeasurementGroupFieldUi): String {
            return when (item) {
                is NumericFieldUiModel -> "číselné"
                else -> "-"
            }
        }
    }

    private class DiffCallback: DiffUtil.ItemCallback<MeasurementGroupFieldUi>() {
        override fun areItemsTheSame(oldItem: MeasurementGroupFieldUi, newItem: MeasurementGroupFieldUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeasurementGroupFieldUi, newItem: MeasurementGroupFieldUi): Boolean {
            return oldItem.name == newItem.name && oldItem.id == newItem.id
        }
    }

    interface FieldAdapterListener {
        fun onFieldClick(item: MeasurementGroupFieldUi, position: Int)
        fun onFieldOptionsMenuClicked(item: MeasurementGroupFieldUi, view: View)
    }

}
