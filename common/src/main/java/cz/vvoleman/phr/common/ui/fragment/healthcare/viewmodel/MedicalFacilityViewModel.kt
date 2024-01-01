package cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalFacilityFragment
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityWithAdditionalInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicalFacilityViewModel : ViewModel() {
    private val _items = MutableStateFlow<UiState<List<MedicalFacilityWithAdditionalInfoUiModel>>>(UiState.Loading)
    val items = _items.asStateFlow()
    private var _listener: MedicalFacilityFragment.MedicalFacilityFragmentInterface? = null

    fun setItems(items: List<MedicalFacilityWithAdditionalInfoUiModel>) = viewModelScope.launch {
        _items.emit(UiState.Success(items))
    }

    fun setListener(listener: MedicalFacilityFragment.MedicalFacilityFragmentInterface) {
        _listener = listener
    }

    fun getListener(): MedicalFacilityFragment.MedicalFacilityFragmentInterface? {
        return _listener
    }
}
