package cz.vvoleman.phr.featureEvent.ui.view.list.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.ItemEventDayItemsBinding
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel

class DayItem(
    private val event: EventUiModel
) : BindableItem<ItemEventDayItemsBinding>() {

    override fun bind(viewBinding: ItemEventDayItemsBinding, position: Int) {
        viewBinding.apply {
            textViewName.text = event.name
            textViewTime.text = event.startAt.toLocalTime().toString()
            textViewInfo.text = event.specificMedicalWorker?.medicalWorker?.name ?: event.description ?: ""
        }
    }

    override fun getLayout() = R.layout.item_event_day_items

    override fun initializeViewBinding(view: View): ItemEventDayItemsBinding {
        return ItemEventDayItemsBinding.bind(view)
    }

}
