package cz.vvoleman.phr.common.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.usecase.GetAllPatientsUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsNotification
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPatientsViewModel @Inject constructor(
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val patientPresentationModelToDomainMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) :
    BaseViewModel<ListPatientsViewState, ListPatientsNotification>(
        savedStateHandle,
        useCaseExecutorProvider
    ) {

    override val TAG = "ListPatientsViewModel"

    override fun initState(): ListPatientsViewState {
        return ListPatientsViewState()
    }

    override fun onInit() {
        super.onInit()

        Log.d(TAG, "launching onInit")

        viewModelScope.launch {
            getAllPatientsUseCase.execute(Unit, ::handlePatientsLoaded)
        }
    }

    private fun handlePatientsLoaded(patients: List<PatientDomainModel>) {
        Log.d(TAG, "handlePatientsLoaded: $patients")
        val mappedPatients = patients.map { patientPresentationModelToDomainMapper.toPresentation(it) }
        updateViewState(currentViewState.copy(patients = mappedPatients))
    }
}