package cz.vvoleman.phr.featureMeasurement.ui.adapter.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import cz.vvoleman.phr.featureMeasurement.databinding.ItemFieldStatsBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.FieldStatsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createHorizontalAxis
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createMarker
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createVerticalAxis
import java.time.LocalDate
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

        private val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()

        fun bind(item: FieldStatsUiModel) {
            if (firstBind) {
                firstBind = false

                val data = getModel(item)
                val producer = getProducer(data)

                val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")
                val formatter = CartesianValueFormatter { x, chartValues, _ ->
                    (chartValues.model.extraStore[xToDateMapKey][x] ?: LocalDate.ofEpochDay(x.toLong()))
                        .format(dateTimeFormatter)
                }
                val marker = createMarker(
                    color = Color.parseColor("#c3c3c3")
                )
                binding.chartView.apply {
                    this.marker = marker
                    data.keys.forEach { key ->
                        this.chart?.addPersistentMarker(key.toEpochDay().toFloat(), marker)
                    }
                    chart?.startAxis = createVerticalAxis(
                        title = "Hodnota",
                    )
//
                    chart?.bottomAxis = createHorizontalAxis(
                        title = "Datum",
                        valueFormatter = formatter
                    )
                    modelProducer = producer
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

        private fun getModel(item: FieldStatsUiModel): Map<LocalDate, Float> {
            val data = item.values.map {
                it.key.toLocalDate() to it.value
            }.toMap()

            return data
        }

        private fun getProducer(data: Map<LocalDate, Float>): CartesianChartModelProducer {
            val xToDates = data.keys.associateBy { it.toEpochDay().toFloat() }

            val producer = CartesianChartModelProducer.build()

            producer.tryRunTransaction {
                lineSeries { series(xToDates.keys, data.values) }
                updateExtras { it[xToDateMapKey] = xToDates }
            }

            return producer
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
