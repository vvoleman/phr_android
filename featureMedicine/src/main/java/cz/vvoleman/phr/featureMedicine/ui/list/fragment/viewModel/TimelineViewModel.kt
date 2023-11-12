package cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel

import androidx.lifecycle.ViewModel
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class TimelineViewModel : ViewModel() {

    private val itemList: MutableList<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>> = mutableListOf()
    private var _listener: TimelineFragment.TimelineInterface? = null
    val isReady: Boolean
        get() = itemList.isNotEmpty() && _listener != null

    fun setItems(items: List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>) {
        itemList.clear()
        itemList.addAll(items)
    }

    fun getItems(): List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>> {
        return itemList.toList()
    }

    fun setListener(listener: TimelineFragment.TimelineInterface) {
        _listener = listener
    }

    fun getListener(): TimelineFragment.TimelineInterface? {
        return _listener
    }

}