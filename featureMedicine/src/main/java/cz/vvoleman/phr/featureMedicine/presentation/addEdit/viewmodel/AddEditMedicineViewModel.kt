package cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMedicine.domain.model.SearchMedicineRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditMedicineViewModel @Inject constructor(
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMedicineViewState, AddEditMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditMedicineViewModel"

    @Suppress("MagicNumber")
    override fun initState(): AddEditMedicineViewState {
        val temp = listOf(LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0))
        val times = temp.map { TimePresentationModel(it, 0) }
        return AddEditMedicineViewState(
            times = times
        )
    }

    fun onSearch(query: String) = viewModelScope.launch {
        val request = SearchMedicineRequestDomainModel(query)
        searchMedicineUseCase.execute(request) {
            updateViewState(currentViewState.copy(medicines = it.map { medicineMapper.toPresentation(it) }))
        }
    }
}
