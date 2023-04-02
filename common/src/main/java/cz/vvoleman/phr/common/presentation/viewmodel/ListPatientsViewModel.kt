package cz.vvoleman.phr.common.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.event.PatientDeletedEvent
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.usecase.DeletePatientUseCase
import cz.vvoleman.phr.common.domain.usecase.GetAllPatientsUseCase
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.SwitchSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsNotification
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class ListPatientsViewModel @Inject constructor(
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val switchSelectedPatientUseCase: SwitchSelectedPatientUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
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

        loadAllPatients()

        listenForPatientChange()
    }

    fun onPatientSwitch(id: String) = viewModelScope.launch {
        switchSelectedPatientUseCase.execute(id) {}
    }

    fun onPatientDelete(patient: PatientPresentationModel) = viewModelScope.launch {
        val domainModel = patientPresentationModelToDomainMapper.toDomain(patient)
        val event = PatientDeletedEvent(domainModel)
        EventBus.getDefault().post(event)
        deletePatientUseCase.execute(patient.id) { success ->
            if (success) {
                notify(ListPatientsNotification.PatientDeleted(patient))
                loadAllPatients()
            } else {
                notify(ListPatientsNotification.PatientDeleteFailed(patient))
            }
        }
    }

    fun onPatientEdit(id: String) = viewModelScope.launch {
        Log.d(TAG, "onEditPatient: $id")
    }

    private fun listenForPatientChange() = viewModelScope.launch {
        val flow = getSelectedPatientUseCase.execute(Unit)

        flow.collect { patient ->
            val mappedPatient = patientPresentationModelToDomainMapper.toPresentation(patient)
            updateViewState(currentViewState.copy(selectedPatientId = mappedPatient.id))
        }
    }

    private fun loadAllPatients() = viewModelScope.launch {
        getAllPatientsUseCase.execute(Unit, ::handlePatientsLoaded)
    }

    private fun handlePatientsLoaded(patients: List<PatientDomainModel>) {
        Log.d(TAG, "handlePatientsLoaded: $patients")
        val mappedPatients = patients.map { patientPresentationModelToDomainMapper.toPresentation(it) }
        updateViewState(currentViewState.copy(patients = mappedPatients))
    }
}