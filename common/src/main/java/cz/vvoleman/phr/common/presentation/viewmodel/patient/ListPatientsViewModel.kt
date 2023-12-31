package cz.vvoleman.phr.common.presentation.viewmodel.patient

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.event.PatientDeletedEvent
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.usecase.patient.DeletePatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetAllPatientsUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.SwitchSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsDestination
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsNotification
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsViewState
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

    override suspend fun initState(): ListPatientsViewState {
        return ListPatientsViewState()
    }

    override suspend fun onInit() {
        super.onInit()

        Log.d(TAG, "launching onInit")

        loadAllPatients()

        listenForPatientChange()
    }

    @Suppress("MagicNumber")
    fun onPatientSwitch(id: String) = viewModelScope.launch {
//        val item = AlarmItem(
//            "switch-$id",
//            LocalDateTime.now().plusSeconds(5),
//            TestContent(id),
//            AlarmReceiver::class.java
//        )
//
//        val result = alarmScheduler.schedule(item)
//        Log.d(TAG, "Scheduled patient: $result")

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

    fun onPatientEdit(id: String) {
        navigateTo(ListPatientsDestination.EditPatient(id))
    }

    fun onPatientAdd() {
        navigateTo(ListPatientsDestination.AddPatient)
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
        val mappedPatients =
            patients.map { patientPresentationModelToDomainMapper.toPresentation(it) }
        updateViewState(currentViewState.copy(patients = mappedPatients))
    }
}
