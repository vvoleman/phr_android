package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemRowItemBinding

class RowItem(
    val text: String
) : BindableItem<ItemRowItemBinding>() {

    override fun bind(viewBinding: ItemRowItemBinding, position: Int) {
        viewBinding.textView.text = text
    }

    override fun getLayout(): Int = R.layout.item_row_item

    override fun initializeViewBinding(view: View): ItemRowItemBinding {
        return ItemRowItemBinding.bind(view)
    }
}
