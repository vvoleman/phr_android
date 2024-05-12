package cz.vvoleman.phr.featureEvent.ui.view.list.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.ItemEventDayItemsBinding
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel

class DayItem(
    private val event: EventUiModel,
) : BindableItem<ItemEventDayItemsBinding>() {

    private var listener: EventItemListener? = null

    override fun bind(viewBinding: ItemEventDayItemsBinding, position: Int) {
        viewBinding.apply {
            buttonOptions.setOnClickListener {
                listener?.onOptionsMenuPopup(event, it)
            }

            textViewName.text = event.name
            textViewTime.text = event.startAt.toLocalTime().toString()
            textViewInfo.text = event.specificMedicalWorker?.medicalWorker?.name ?: event.description ?: ""
        }
    }

    fun setListener(listener: EventItemListener) {
        this.listener = listener
    }

    override fun getLayout() = R.layout.item_event_day_items

    override fun initializeViewBinding(view: View): ItemEventDayItemsBinding {
        return ItemEventDayItemsBinding.bind(view)
    }

    interface EventItemListener {
        fun onOptionsMenuPopup(item: EventUiModel, view: View)
    }
}
