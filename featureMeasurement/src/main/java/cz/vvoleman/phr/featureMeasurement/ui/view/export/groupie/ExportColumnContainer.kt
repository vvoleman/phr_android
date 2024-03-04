package cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ExportItemColumnBinding

class ExportColumnContainer(
    private val items: List<BindableItem<*>>
) : BindableItem<ExportItemColumnBinding>() {

    override fun bind(viewBinding: ExportItemColumnBinding, position: Int) {
        for (i in items.indices) {
            when (val item = items[i]) {
                is ExportRowItem -> item.setItemIndex(i)
            }
        }

        val groupieAdapter = GroupieAdapter().apply {
            addAll(items)
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                viewBinding.root.context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    override fun getLayout(): Int = R.layout.export_item_column

    override fun initializeViewBinding(view: View): ExportItemColumnBinding {
        return ExportItemColumnBinding.bind(view)
    }
}
