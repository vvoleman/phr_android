package cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemButtonItemBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel

class ButtonItem(
    val id: EntryInfoUiModel,
    val callback: (EntryInfoUiModel, View) -> Unit
) : BindableItem<ItemButtonItemBinding>() {

    private var itemIndex: Int? = null

    override fun bind(viewBinding: ItemButtonItemBinding, position: Int) {
        itemIndex?.let {
            if (it % 2 == 0) {
                viewBinding.root.setBackgroundColor(viewBinding.root.context.getColor(cz.vvoleman.phr.base.R.color.gray_500))
            } else {
//                viewBinding.root.setBackgroundColor(viewBinding.root.context.getColor(cz.vvoleman.phr.base.R.color.gray_200))
            }
        }

        viewBinding.button.setOnClickListener {
            callback(id, viewBinding.root)
        }
    }

    fun setItemIndex(index: Int) {
        itemIndex = index
    }

    override fun getLayout(): Int = R.layout.item_button_item

    override fun initializeViewBinding(view: View): ItemButtonItemBinding {
        return ItemButtonItemBinding.bind(view)
    }
}
