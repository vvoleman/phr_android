package cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ExportItemRowItemBinding

class ExportRowItem(
    val text: String
) : BindableItem<ExportItemRowItemBinding>() {

    private var itemIndex: Int? = null

    override fun bind(viewBinding: ExportItemRowItemBinding, position: Int) {
        viewBinding.textView.text = text

        itemIndex?.let {
            if (it % 2 == 0) {
                viewBinding.root.setBackgroundColor(viewBinding.root.context.getColor(cz.vvoleman.phr.base.R.color.gray_500))
            } else {
//                viewBinding.root.setBackgroundColor(viewBinding.root.context.getColor(cz.vvoleman.phr.base.R.color.gray_200))
            }
        }
    }

    fun setItemIndex(index: Int) {
        itemIndex = index
    }

    override fun getLayout(): Int = R.layout.export_item_row_item

    override fun initializeViewBinding(view: View): ExportItemRowItemBinding {
        return ExportItemRowItemBinding.bind(view)
    }
}
