package cz.vvoleman.phr.featureMedicine.ui.export.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.common.utils.getLocalString
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemDocumentPageMedicineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel

class MedicinePageAdapter :
    ListAdapter<MedicineScheduleUiModel, MedicinePageAdapter.MedicineViewHolder>(DiffCallback()) {

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
            // If item.getDays().size is 7, set to "Každý den"
            val days = getDaysString(item)
            val times = getTimesString(item)

            val resources = binding.root.resources

            val createdAt = item.createdAt.toLocalDate().toLocalString()
            val endingAt = item.schedules.first().endingAt?.toLocalDate()?.toLocalString()
            val dateRangeString = if (endingAt != null) {
                resources.getString(R.string.document_medicine_item_date_range_expired, createdAt, endingAt)
            } else {
                resources.getString(R.string.document_medicine_item_date_range, createdAt)
            }

            binding.apply {
                textViewName.text = item.medicine.name
                textViewFrequency.text = days
                textViewTimes.text = times
                textViewDateRange.text = dateRangeString
            }
        }

        private fun getDaysString(item: MedicineScheduleUiModel): String {
            val days = item.getDays()
            return if (days.size == TimeConstants.DAYS_IN_WEEK) {
                "Každý den"
            } else {
                days.joinToString(",") { it.getLocalString() }
            }
        }

        private fun getTimesString(item: MedicineScheduleUiModel): String {
            // Get schedules for one specific week day only
            val schedules = item.schedules.groupBy { it.dayOfWeek }.values.first()
            return schedules.joinToString(", ") {
                "${it.time} (${it.quantity} ${it.unit})"
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
