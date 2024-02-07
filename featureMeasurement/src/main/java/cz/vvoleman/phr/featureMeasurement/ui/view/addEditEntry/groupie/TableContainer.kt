package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemColumnBinding
import cz.vvoleman.phr.featureMeasurement.databinding.TableContainerBinding

class TableContainer(
    private val columns: List<BindableItem<ItemColumnBinding>>,
) : BindableItem<TableContainerBinding>() {

    override fun bind(viewBinding: TableContainerBinding, position: Int) {
        val groupieAdapter = GroupieAdapter().apply { addAll(columns) }

        // LinearLayoutManager with orientation set to HORIZONTAL
        val linearManager = androidx.recyclerview.widget.LinearLayoutManager(
            viewBinding.root.context,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = linearManager
            setHasFixedSize(true)
        }
    }

    override fun getLayout(): Int = R.layout.table_container

    override fun initializeViewBinding(view: View): TableContainerBinding {
        return TableContainerBinding.bind(view)
    }
}
