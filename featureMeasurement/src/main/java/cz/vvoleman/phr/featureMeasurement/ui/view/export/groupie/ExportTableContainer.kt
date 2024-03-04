package cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ExportItemColumnBinding
import cz.vvoleman.phr.featureMeasurement.databinding.ExportTableContainerBinding

class ExportTableContainer(
    private val columns: List<BindableItem<ExportItemColumnBinding>>,
) : BindableItem<ExportTableContainerBinding>() {

    override fun bind(viewBinding: ExportTableContainerBinding, position: Int) {
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

    override fun getLayout(): Int = R.layout.export_table_container

    override fun initializeViewBinding(view: View): ExportTableContainerBinding {
        return ExportTableContainerBinding.bind(view)
    }
}
