package cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemRowItemBinding

class RowItem(
    val text: String
) : BindableItem<ItemRowItemBinding>() {

    private var itemIndex: Int? = null

    override fun bind(viewBinding: ItemRowItemBinding, position: Int) {
        viewBinding.textView.text = text

        itemIndex?.let {
            if (it % 2 == 0) {
                viewBinding.root.setBackgroundColor(
                    viewBinding.root.context.getColor(cz.vvoleman.phr.base.R.color.gray_500)
                )
            }
        }
    }

    fun setItemIndex(index: Int) {
        itemIndex = index
    }

    override fun getLayout(): Int = R.layout.item_row_item

    override fun initializeViewBinding(view: View): ItemRowItemBinding {
        return ItemRowItemBinding.bind(view)
    }
}
