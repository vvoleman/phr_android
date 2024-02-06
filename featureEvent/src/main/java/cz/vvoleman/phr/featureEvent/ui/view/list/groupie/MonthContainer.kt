package cz.vvoleman.phr.featureEvent.ui.view.list.groupie

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.getNameOfMonth
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.ItemEventMonthBinding
import java.time.LocalDate

class MonthContainer(
    private val date: LocalDate,
    private val items: List<BindableItem<*>>
) : BindableItem<ItemEventMonthBinding>() {

    override fun bind(viewBinding: ItemEventMonthBinding, position: Int) {
        viewBinding.apply {
            textViewMonth.text = "${date.getNameOfMonth()} ${date.year}"

            val groupieAdapter = GroupieAdapter().apply {
                addAll(items)
            }

            recyclerView.apply {
                adapter = groupieAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }

    override fun getLayout() = R.layout.item_event_month

    override fun initializeViewBinding(view: View): ItemEventMonthBinding {
        return ItemEventMonthBinding.bind(view)
    }
}
