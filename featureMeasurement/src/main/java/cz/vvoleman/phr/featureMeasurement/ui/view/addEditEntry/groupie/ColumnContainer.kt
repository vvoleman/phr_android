package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemColumnBinding

class ColumnContainer(
    private val items: List<BindableItem<*>>
) : BindableItem<ItemColumnBinding>() {

    override fun bind(viewBinding: ItemColumnBinding, position: Int) {
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

    override fun getLayout(): Int = R.layout.item_column

    override fun initializeViewBinding(view: View): ItemColumnBinding {
        return ItemColumnBinding.bind(view)
    }
}
