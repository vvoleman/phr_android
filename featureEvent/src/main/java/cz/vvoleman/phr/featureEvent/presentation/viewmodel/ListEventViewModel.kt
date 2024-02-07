package cz.vvoleman.phr.featureEvent.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventsByPatientRepository
import cz.vvoleman.phr.featureEvent.presentation.mapper.core.EventPresentationModelToDomainMapper
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventDestination
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListEventViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getEventsByPatientRepository: GetEventsByPatientRepository,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val eventMapper: EventPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<ListEventViewState, ListEventNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListEventViewModel"

    override suspend fun initState(): ListEventViewState {
        val patient = getSelectedPatient()
        val events = getEvents(patient)

        Log.d(TAG, "initState: $patient, $events")

        return ListEventViewState(
            patient = patient,
            events = events
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getEvents(patient: PatientPresentationModel): Map<LocalDate, List<EventPresentationModel>> {
        return getEventsByPatientRepository
            .getEventsByPatient(patient.id)
            .map { eventMapper.toPresentation(it) }
            .groupBy { it.startAt.toLocalDate() }
            .toSortedMap()
            .mapValues { (_, values) ->
                values.sortedBy { it.startAt }
            }
    }

    fun onAddEvent() {
        navigateTo(ListEventDestination.AddEvent)
    }

    fun onExportEvents() {
        val events = currentViewState.events.map { it.value }.flatten()
        notify(ListEventNotification.ExportEvents(events))
    }
}
