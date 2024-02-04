package cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie

import android.view.View
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ItemDetailSectionBinding

class SectionContainer(
    private val title: String,
    private val icon: Int,
    private val description: String,
    private val items: List<BindableItem<*>>
) : BindableItem<ItemDetailSectionBinding>() {

    override fun bind(viewBinding: ItemDetailSectionBinding, position: Int) {
        viewBinding.apply {
            titleTextView.text = title
            descriptionTextView.text = description
            val groupieAdapter = GroupieAdapter().apply {
                addAll(items)
            }

            // create flex layout manager and make it grid

            itemsContainer.apply {
                adapter = groupieAdapter
                addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
                setHasFixedSize(true)
            }
        }
    }

    override fun getLayout() = R.layout.item_detail_section

    override fun initializeViewBinding(view: View): ItemDetailSectionBinding {
        return ItemDetailSectionBinding.bind(view)
    }
}
