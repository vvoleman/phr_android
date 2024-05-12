package cz.vvoleman.phr.featureEvent.ui.view.list.groupie

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common.utils.getLocalString
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.ItemEventDayBinding
import java.time.LocalDate
import java.time.format.TextStyle

class DayContainer(
    private val date: LocalDate,
    private val items: List<BindableItem<*>>
) : BindableItem<ItemEventDayBinding>() {

    override fun bind(viewBinding: ItemEventDayBinding, position: Int) {
        viewBinding.apply {
            if (date.isBefore(LocalDate.now())) {
                root.alpha = 0.5f
            }

            textViewDay.text = date.dayOfMonth.toString()
            textViewWeekDay.text = date.dayOfWeek.getLocalString(TextStyle.SHORT)

            val groupieAdapter = GroupieAdapter().apply {
                addAll(items)
            }

            recyclerView.apply {
                adapter = groupieAdapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
                setHasFixedSize(true)
            }
        }
    }

    override fun getLayout() = R.layout.item_event_day

    override fun initializeViewBinding(view: View): ItemEventDayBinding {
        return ItemEventDayBinding.bind(view)
    }
}
