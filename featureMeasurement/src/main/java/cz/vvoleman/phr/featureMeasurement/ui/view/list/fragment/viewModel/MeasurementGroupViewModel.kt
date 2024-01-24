package cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMeasurement.ui.adapter.list.MeasurementGroupAdapter
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeasurementGroupViewModel : ViewModel() {

    private val _items = MutableStateFlow<UiState<List<GroupedItemsUiModel<MeasurementGroupUiModel>>>>(UiState.Loading)
    val items = _items.asStateFlow()
    private var listener: MeasurementGroupAdapter.MeasurementGroupAdapterInterface? = null

    fun setItems(allGroups: List<GroupedItemsUiModel<MeasurementGroupUiModel>>) = viewModelScope.launch {
        _items.emit(UiState.Success(allGroups))
    }

    fun setListener(listener: MeasurementGroupAdapter.MeasurementGroupAdapterInterface) {
        this.listener = listener
    }

    fun getListener(): MeasurementGroupAdapter.MeasurementGroupAdapterInterface? {
        return listener
    }

}
