package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@Suppress("UnusedPrivateProperty")
class ListMedicineViewModel @Inject constructor(
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override fun initState(): ListMedicineViewState {
        return ListMedicineViewState()
    }

    fun onCreate() {
        navigateTo(ListMedicineDestination.CreateSchedule)
    }

    fun onEdit(id: String) {
        navigateTo(ListMedicineDestination.EditSchedule(id))
    }
}
