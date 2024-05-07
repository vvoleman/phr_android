package cz.vvoleman.phr.featureMeasurement.ui.subscriber

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemProblemCategoryDetailBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.MeasurementGroupWithStatsUiModel

class MeasurementGroupItem(
    private val item: MeasurementGroupWithStatsUiModel,
    private val onClick: (String) -> Unit
) : BindableItem<ItemProblemCategoryDetailBinding>() {
    override fun bind(viewBinding: ItemProblemCategoryDetailBinding, position: Int) {
        val items = item.fieldStats.map { MeasurementGroupFieldItem(it) {
            onClick(item.measurementGroup.id)
        } }

        val groupieAdapter = GroupieAdapter()
        groupieAdapter.addAll(items)

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }

        viewBinding.root.setOnClickListener {
            onClick(item.measurementGroup.id)
        }
        viewBinding.textViewName.text = item.measurementGroup.name
    }

    override fun getLayout() = R.layout.item_problem_category_detail

    override fun initializeViewBinding(view: View): ItemProblemCategoryDetailBinding {
        return ItemProblemCategoryDetailBinding.bind(view)
    }
}
