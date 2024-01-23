package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.domain.facade.NextMeasurementGroupScheduleFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.list.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GetNextScheduledMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ListMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getNextScheduledMeasurementGroupUseCase: GetNextScheduledMeasurementGroupUseCase,
    private val nextMeasurementGroupScheduleFacade: NextMeasurementGroupScheduleFacade,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMeasurementViewState, ListMeasurementNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMeasurementViewModel"

    override suspend fun initState(): ListMeasurementViewState {
        val patient = getSelectedPatient()
        val scheduled = getNextScheduled(patient.id)

        return ListMeasurementViewState(
            patient = patient,
            nextSchedules = getNextScheduled(patient.id),
            selectedNextSchedule = getNextSelectedSchedule(scheduled)
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getNextScheduled(patientId: String): List<MeasurementGroupPresentationModel> {
        val request = NextScheduledRequestDomainModel(
            patientId = patientId,
            currentLocalDateTime = LocalDateTime.now(),
        )

        val nextSchedules = getNextScheduledMeasurementGroupUseCase.executeInBackground(request)

        return nextSchedules.map { measurementGroupMapper.toPresentation(it) }
    }

    fun onAddMeasurementGroup() {
        navigateTo(ListMeasurementDestination.AddMeasurementGroup)
    }

    fun onNextScheduleTimeOut() {
        Log.d(TAG, "onNextScheduleTimeOut: ${currentViewState.nextSchedules.size} items")
        if (currentViewState.nextSchedules.isNotEmpty()) {
            val list = currentViewState.nextSchedules.toMutableList()
            list.removeFirst()
            val selectedSchedule = getNextSelectedSchedule(list)

            updateViewState(
                currentViewState.copy(
                    nextSchedules = list,
                    selectedNextSchedule = selectedSchedule
                )
            )
            return
        }
    }

    private fun getNextSelectedSchedule(
        list: List<MeasurementGroupPresentationModel>
    ): MeasurementGroupPresentationModel? {
        var selectedSchedule: MeasurementGroupPresentationModel? = null
        if (list.isNotEmpty()) {
            val listDomain = list.map { measurementGroupMapper.toDomain(it) }

            selectedSchedule = nextMeasurementGroupScheduleFacade
                .getNextSchedule(listDomain, LocalDateTime.now())
                .firstOrNull()
                ?.let { measurementGroupMapper.toPresentation(it) }
        }

        return selectedSchedule
    }

}
