package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.domain.facade.NextMeasurementGroupScheduleFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.list.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GetNextScheduledMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.ScheduledMeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.list.MeasurementGroupPresentationModelToNextScheduleMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ListMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getNextScheduledMeasurementGroupUseCase: GetNextScheduledMeasurementGroupUseCase,
    private val nextMeasurementGroupScheduleFacade: NextMeasurementGroupScheduleFacade,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    private val nextScheduleMapper: MeasurementGroupPresentationModelToNextScheduleMapper,
    private val scheduledMapper: ScheduledMeasurementGroupPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMeasurementViewState, ListMeasurementNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMeasurementViewModel"

    override suspend fun initState(): ListMeasurementViewState {
        val patient = getSelectedPatient()
        val scheduled = getNextScheduled(patient.id)

        return ListMeasurementViewState(
            patient = patient,
            nextSchedules = scheduled,
            selectedNextSchedule = scheduled.firstOrNull()
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getNextScheduled(
        patientId: String,
        dateTime: LocalDateTime = LocalDateTime.now()
    ): List<NextSchedulePresentationModel> {
        val request = NextScheduledRequestDomainModel(
            patientId = patientId,
            currentLocalDateTime = dateTime,
        )

        val nextSchedules = getNextScheduledMeasurementGroupUseCase.executeInBackground(request)
        val groupedByDateTime = nextSchedules
            .map { scheduledMapper.toPresentation(it) }
            .groupBy { it.dateTime }
            .toSortedMap()
            .map { (_, list) ->
                nextScheduleMapper.toNextSchedule(list)
            }

        return groupedByDateTime
    }

    fun onAddMeasurementGroup() {
        navigateTo(ListMeasurementDestination.AddMeasurementGroup)
    }

    fun onNextScheduleTimeOut() = viewModelScope.launch {
        Log.d(TAG, "onNextScheduleTimeOut: ${currentViewState.nextSchedules.size} items")
        reloadNextSchedules()
        if (currentViewState.nextSchedules.isNotEmpty()) {
            val list = currentViewState.nextSchedules.toMutableList()
            list.removeFirst()
            val selectedSchedule = list.firstOrNull()

            updateViewState(
                currentViewState.copy(
                    nextSchedules = list,
                    selectedNextSchedule = selectedSchedule
                )
            )
            return@launch
        }
    }

    private suspend fun reloadNextSchedules() {
        val list = currentViewState.nextSchedules

        if (list.size >= 2) {
            return
        }

        val dateTime = if (list.size == 1) {
            list.first().dateTime.plusMinutes(1)
        } else {
            LocalDateTime.now()
        }

        val result = getNextScheduled(
            patientId = currentViewState.patient.id,
            dateTime = dateTime
        )

        updateViewState(
            currentViewState.copy(
                nextSchedules = result
            )
        )
    }

}
