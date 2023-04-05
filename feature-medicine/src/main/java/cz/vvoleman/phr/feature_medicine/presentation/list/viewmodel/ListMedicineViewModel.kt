package cz.vvoleman.phr.feature_medicine.presentation.list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicine.domain.model.SearchMedicineRequestDomainModel
import cz.vvoleman.phr.feature_medicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMedicineViewModel @Inject constructor(
    private val searchMedicineUseCase: SearchMedicineUseCase,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override fun initState(): ListMedicineViewState {
        return ListMedicineViewState()
    }

    fun onSearch(query: String) = viewModelScope.launch {
        val request = SearchMedicineRequestDomainModel(query)
        searchMedicineUseCase.execute(request) {
            updateViewState(currentViewState.copy(medicines = it))
        }
    }

}