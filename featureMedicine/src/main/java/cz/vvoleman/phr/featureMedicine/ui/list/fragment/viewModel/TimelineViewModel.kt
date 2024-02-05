package cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimelineViewModel : ViewModel() {

    private val _items: MutableStateFlow<UiState<List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>>> =
        MutableStateFlow(UiState.Loading)
    val items = _items.asStateFlow()
    private var _listener: TimelineFragment.TimelineInterface? = null

    fun setItems(items: List<GroupedItemsUiModel<ScheduleItemWithDetailsUiModel>>) = viewModelScope.launch {
        _items.emit(UiState.Success(items))
    }

    fun setListener(listener: TimelineFragment.TimelineInterface) {
        _listener = listener
    }

    fun getListener(): TimelineFragment.TimelineInterface? {
        return _listener
    }
}
