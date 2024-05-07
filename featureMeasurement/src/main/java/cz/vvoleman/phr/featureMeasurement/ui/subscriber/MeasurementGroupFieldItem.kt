package cz.vvoleman.phr.featureMeasurement.ui.subscriber

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemProblemCategoryDetailFieldBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.FieldStatsUiModel

class MeasurementGroupFieldItem(
    private val item: FieldStatsUiModel,
    private val onClick: () -> Unit
) : BindableItem<ItemProblemCategoryDetailFieldBinding>() {

    override fun bind(viewBinding: ItemProblemCategoryDetailFieldBinding, position: Int) {
        val resources = viewBinding.root.resources
        val lastEntryValue = item.values.maxByOrNull { it.key }
        val minimum = item.values.minByOrNull { it.value }
        val maximum = item.values.maxByOrNull { it.value }
        val average = item.weekAvgValue
        viewBinding.apply {
            root.setOnClickListener {
                onClick()
            }
            textViewName.text = item.name

            if (lastEntryValue != null) {
                textViewLastEntryValue.text = resources.getString(
                    R.string.measurement_field_info,
                    lastEntryValue.value.toString(),
                    lastEntryValue.key.toLocalDate().toLocalString()
                )
            }

            if (minimum != null) {
                textViewMinimumValue.text = resources.getString(
                    R.string.measurement_field_info,
                    minimum.value.toString(),
                    minimum.key.toLocalDate().toLocalString()
                )
            }

            if (maximum != null) {
                textViewMaximumValue.text = resources.getString(
                    R.string.measurement_field_info,
                    maximum.value.toString(),
                    maximum.key.toLocalDate().toLocalString()
                )
            }

            if (item.weekAvgValue != null) {
                textViewWeekAverageValue.text = average.toString()
            }

        }
    }

    override fun getLayout() = R.layout.item_problem_category_detail_field

    override fun initializeViewBinding(view: View): ItemProblemCategoryDetailFieldBinding {
        return ItemProblemCategoryDetailFieldBinding.bind(view)
    }
}
