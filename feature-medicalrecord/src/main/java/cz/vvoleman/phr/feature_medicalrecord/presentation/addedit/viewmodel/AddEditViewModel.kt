package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditPresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientDomainModelToPresentation: PatientDomainModelToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditNotification>(savedStateHandle,useCaseExecutorProvider){

    override val TAG = "AddEditViewModel"

    override fun initState(): AddEditViewState {
        return AddEditViewState()
    }

    init {
        loadSelectedPatient()
    }

    override fun onInit() {
        super.onInit()
        loadSelectedPatient()
    }

    fun onSubmit(input: AddEditPresentationModel) {

    }

    private fun loadSelectedPatient() = viewModelScope.launch {
        val patient = getSelectedPatientUseCase.execute(null).first()
        Log.d(TAG, "loadSelectedPatient: $patient")
        updateViewState(currentViewState.copy(patient = patientDomainModelToPresentation.toPresentation(patient)))
    }

}