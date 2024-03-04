package cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ExportItemHeaderItemBinding

class ExportHeaderItem(
    private val text: String
) : BindableItem<ExportItemHeaderItemBinding>() {

    override fun bind(viewBinding: ExportItemHeaderItemBinding, position: Int) {
        viewBinding.textView.text = text
    }

    override fun getLayout(): Int = R.layout.export_item_header_item
    override fun initializeViewBinding(view: View): ExportItemHeaderItemBinding {
        return ExportItemHeaderItemBinding.bind(view)
    }
}
