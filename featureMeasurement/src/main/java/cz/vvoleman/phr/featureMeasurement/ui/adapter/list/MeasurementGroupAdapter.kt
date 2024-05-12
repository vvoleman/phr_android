package cz.vvoleman.phr.featureMeasurement.ui.adapter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import java.time.format.TextStyle
import java.util.Locale

class MeasurementGroupAdapter(
    private val listener: MeasurementGroupAdapterInterface
) : ListAdapter<MeasurementGroupUiModel, MeasurementGroupAdapter.MeasurementGroupViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementGroupViewHolder {
        val binding = ItemMeasurementGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeasurementGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeasurementGroupViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class MeasurementGroupViewHolder(private val binding: ItemMeasurementGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onMeasurementGroupClick(item)
                }
            }
            binding.buttonOptions.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onMeasurementGroupOptionsClick(item, binding.buttonOptions)
                }
            }
        }

        fun bind(item: MeasurementGroupUiModel) {
            val times = item.getTimes().joinToString(SEPARATOR)
            val frequencies =
                item.getDays().map { it.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()) }
                    .let {
                        if (it.size == TimeConstants.DAYS_IN_WEEK) {
                            binding.root.context.getString(R.string.frequency_everyday)
                        } else {
                            it.joinToString(SEPARATOR)
                        }
                    }

            binding.apply {
                textViewName.text = item.name
                textViewTimes.text = times
                textViewFrequency.text = frequencies
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MeasurementGroupUiModel>() {
        override fun areItemsTheSame(oldItem: MeasurementGroupUiModel, newItem: MeasurementGroupUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeasurementGroupUiModel, newItem: MeasurementGroupUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface MeasurementGroupAdapterInterface {
        fun onMeasurementGroupClick(item: MeasurementGroupUiModel)
        fun onMeasurementGroupOptionsClick(item: MeasurementGroupUiModel, anchorView: View)
    }

    companion object {
        private const val SEPARATOR = " | "
    }
}
