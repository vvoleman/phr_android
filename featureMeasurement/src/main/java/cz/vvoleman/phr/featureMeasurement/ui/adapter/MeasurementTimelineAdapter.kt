package cz.vvoleman.phr.featureMeasurement.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMeasurement.databinding.ItemMeasurementTimelineBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel

class MeasurementTimelineAdapter(
    private val listener: MeasurementTimelineAdapterInterface
) : ListAdapter<ScheduledMeasurementGroupUiModel, MeasurementTimelineAdapter.MeasurementTimelineViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementTimelineViewHolder {
        val binding = ItemMeasurementTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeasurementTimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeasurementTimelineViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class MeasurementTimelineViewHolder(private val binding: ItemMeasurementTimelineBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onMeasurementTimelineClick(item)
                }
            }

            binding.buttonEntry.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    listener.onMeasurementTimelineMakeEntryClick(item)
                }
            }
        }

        fun bind(item: ScheduledMeasurementGroupUiModel) {
            binding.textViewName.text = item.measurementGroup.name
            binding.textViewInfoTime.text = item.dateTime.toLocalTime().toString()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ScheduledMeasurementGroupUiModel>() {
        override fun areItemsTheSame(
            oldItem: ScheduledMeasurementGroupUiModel,
            newItem: ScheduledMeasurementGroupUiModel
        ): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: ScheduledMeasurementGroupUiModel, newItem: ScheduledMeasurementGroupUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface MeasurementTimelineAdapterInterface {
        fun onMeasurementTimelineClick(item: ScheduledMeasurementGroupUiModel)
        fun onMeasurementTimelineMakeEntryClick(item: ScheduledMeasurementGroupUiModel)
    }
}
