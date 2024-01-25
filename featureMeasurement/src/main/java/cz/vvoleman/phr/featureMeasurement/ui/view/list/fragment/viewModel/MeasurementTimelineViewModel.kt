package cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.adapter.MeasurementTimelineAdapter
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeasurementTimelineViewModel: ViewModel() {

    private val _items = MutableStateFlow<UiState<List<GroupedItemsUiModel<ScheduledMeasurementGroupUiModel>>>>(UiState.Loading)
    val items = _items.asStateFlow()
    private var listener: MeasurementTimelineAdapter.MeasurementTimelineAdapterInterface? = null

    fun setItems(items: List<GroupedItemsUiModel<ScheduledMeasurementGroupUiModel>>) = viewModelScope.launch {
        _items.emit(UiState.Success(items))
    }

    fun setListener(listener: MeasurementTimelineAdapter.MeasurementTimelineAdapterInterface) {
        this.listener = listener
    }

    fun getListener(): MeasurementTimelineAdapter.MeasurementTimelineAdapterInterface? {
        return listener
    }

}
