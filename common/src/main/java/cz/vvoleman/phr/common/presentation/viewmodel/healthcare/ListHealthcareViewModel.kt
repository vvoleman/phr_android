package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHealthcareViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getMedicalWorkersUseCase: GetMedicalWorkersUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListHealthcareViewState, ListHealthcareNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListHealthcareViewModel"

    override fun initState(): ListHealthcareViewState {
        return ListHealthcareViewState()
    }

    override fun onInit() {
        super.onInit()

        Log.d(TAG, "launching onInit")

        viewModelScope.launch {
            loadSelectedPatient()
        }
    }

    fun onClick() = viewModelScope.launch {
        val result = getMedicalWorkersUseCase.execute(GetMedicalWorkersRequest(currentViewState.patient!!.id)) {
            Log.d(TAG, "callback: $it")
        }
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }
}
