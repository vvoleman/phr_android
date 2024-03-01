package cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemHeaderItemBinding

class HeaderItem(
    private val text: String
) : BindableItem<ItemHeaderItemBinding>() {

    override fun bind(viewBinding: ItemHeaderItemBinding, position: Int) {
        viewBinding.textView.text = text
    }

    override fun getLayout(): Int = R.layout.item_header_item
    override fun initializeViewBinding(view: View): ItemHeaderItemBinding {
        return ItemHeaderItemBinding.bind(view)
    }
}
