package cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalWorkerFragment
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerWithAdditionalInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicalWorkerViewModel : ViewModel() {

    private val _items = MutableStateFlow<UiState<List<MedicalWorkerWithAdditionalInfoUiModel>>>(UiState.Loading)
    val items = _items.asStateFlow()
    private var _listener: MedicalWorkerFragment.MedicalWorkerFragmentInterface? = null

    fun setItems(items: List<MedicalWorkerWithAdditionalInfoUiModel>) = viewModelScope.launch {
        _items.emit(UiState.Success(items))
    }

    fun setListener(listener: MedicalWorkerFragment.MedicalWorkerFragmentInterface) {
        _listener = listener
    }

    fun getListener(): MedicalWorkerFragment.MedicalWorkerFragmentInterface? {
        return _listener
    }


}
