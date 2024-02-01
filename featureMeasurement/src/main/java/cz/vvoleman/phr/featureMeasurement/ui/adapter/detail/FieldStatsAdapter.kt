package cz.vvoleman.phr.featureMeasurement.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.views.dimensions.dimensionsOf
import cz.vvoleman.phr.featureMeasurement.databinding.ItemFieldStatsBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.FieldStatsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createHorizontalAxis
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createMarker
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createVerticalAxis
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class FieldStatsAdapter :
    ListAdapter<FieldStatsUiModel, FieldStatsAdapter.FieldStatsRecyclerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldStatsRecyclerViewHolder {
        val binding = ItemFieldStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldStatsRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldStatsRecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class FieldStatsRecyclerViewHolder(private val binding: ItemFieldStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var firstBind = true

        fun bind(item: FieldStatsUiModel) {
            if (firstBind) {
                firstBind = false

                val producer = getProducer(item)
                binding.chartView.apply {
                    marker = createMarker()
                    startAxis = createVerticalAxis(
                        title = item.unit,
                        titleMargins = dimensionsOf(0f, 0f, 10f, 0f),
                        labelMargins = dimensionsOf(0f, 0f, 5f, 0f),
                    )

                    bottomAxis = createHorizontalAxis(
                        title = "Datum",
                        valueFormatter = { value, _ ->
                            val date = LocalDate.ofEpochDay(value.toLong())
                            date.format(DateTimeFormatter.ofPattern("dd.MM"))
                        }
                    )
                    entryProducer = producer
                }
            }

            binding.apply {
                textViewName.text = item.name
                textViewMinimumValue.text = item.minValue.toString()
                textViewMaximumValue.text = item.maxValue.toString()
                textViewWeekAverageValue.text = item.weekAvgValue.toString()
                textViewEntryCountValue.text = item.values.size.toString()
            }
        }

        private fun getProducer(item: FieldStatsUiModel): ChartEntryModelProducer {
            val entries = item.values.map { (dateTime, value) ->
                entryOf(dateTime.toLocalDate().toEpochDay(), value)
            }

            return ChartEntryModelProducer(entries)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FieldStatsUiModel>() {
        override fun areItemsTheSame(oldItem: FieldStatsUiModel, newItem: FieldStatsUiModel): Boolean {
            return oldItem.fieldId == newItem.fieldId
        }

        override fun areContentsTheSame(oldItem: FieldStatsUiModel, newItem: FieldStatsUiModel): Boolean {
            return oldItem == newItem
        }
    }

}
