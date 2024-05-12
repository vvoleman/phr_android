package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.enum.SortDirection
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.grouped.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.DeleteMeasurementGroupRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GetScheduledMeasurementGroupInTimeRangeRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GroupMeasurementGroupRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GroupScheduledMeasurementsRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit.ScheduleMeasurementGroupAlertUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.DeleteMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GetNextScheduledMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GetScheduledMeasurementGroupInTimeRangeUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GroupMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GroupScheduledMeasurementsByTimeUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.ScheduledMeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.list.MeasurementGroupPresentationModelToNextScheduleMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.ScheduledMeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ListMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getNextScheduledMeasurementGroupUseCase: GetNextScheduledMeasurementGroupUseCase,
    private val groupMeasurementGroupUseCase: GroupMeasurementGroupUseCase,
    private val deleteMeasurementGroupUseCase: DeleteMeasurementGroupUseCase,
    private val getScheduledMeasurementGroupInTimeRangeUseCase: GetScheduledMeasurementGroupInTimeRangeUseCase,
    private val scheduleMeasurementGroupUseCase: ScheduleMeasurementGroupAlertUseCase,
    private val groupScheduledMeasurementsByTimeUseCase: GroupScheduledMeasurementsByTimeUseCase,
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
        val scheduled = getNextScheduled(patient.id).toMutableList()
        val nextSchedule = scheduled.removeFirstOrNull()
        val direction = GroupMeasurementGroupRequest.OrderByDirection.ASC
        val grouped = getGroupedMeasurementGroups(patient.id, direction)
        val timelineSchedules = getTimelineSchedules(patient.id, LocalDateTime.now())

        return ListMeasurementViewState(
            patient = patient,
            nextSchedules = scheduled,
            selectedNextSchedule = nextSchedule,
            groupDirection = direction,
            groupedMeasurementGroups = grouped,
            timelineSchedules = timelineSchedules,
        )
    }

    fun onEditMeasurementGroup(id: String) {
        navigateTo(ListMeasurementDestination.EditMeasurementGroup(id))
    }

    fun onDeleteMeasurementGroup(measurementGroup: MeasurementGroupPresentationModel) = viewModelScope.launch {
        val model = measurementGroupMapper.toDomain(measurementGroup)
        val request = DeleteMeasurementGroupRequest(measurementGroup = model)
        deleteMeasurementGroupUseCase.execute(request, ::handleDeleteMeasurementGroup)
    }

    fun onAddEntry(model: ScheduledMeasurementGroupPresentationModel) {
        navigateTo(ListMeasurementDestination.AddEntry(model.measurementGroup.id))
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

    private suspend fun getGroupedMeasurementGroups(
        patientId: String,
        orderByDirection: GroupMeasurementGroupRequest.OrderByDirection
    ): List<GroupedItemsPresentationModel<MeasurementGroupPresentationModel>> {
        val request = GroupMeasurementGroupRequest(
            patientId = patientId,
            orderByDirection = orderByDirection
        )

        val groupedMeasurementGroups = groupMeasurementGroupUseCase.executeInBackground(request)
        return groupedMeasurementGroups.map {
            GroupedItemsPresentationModel(
                value = it.value,
                items = it.items.map { group -> measurementGroupMapper.toPresentation(group) }
            )
        }
    }

    private suspend fun getTimelineSchedules(
        patientId: String,
        currentDateTime: LocalDateTime
    ): List<GroupedItemsPresentationModel<ScheduledMeasurementGroupPresentationModel>> {
        val date = currentDateTime.toLocalDate()

        val rangeRequest = GetScheduledMeasurementGroupInTimeRangeRequest(
            patientId = patientId,
            startAt = date.atTime(LocalTime.MIN),
            endAt = date.atTime(LocalTime.MAX)
        )
        val schedules = getScheduledMeasurementGroupInTimeRangeUseCase.executeInBackground(rangeRequest)

        val groupRequest = GroupScheduledMeasurementsRequest(
            scheduleItems = schedules,
            sortDirection = SortDirection.ASC
        )

        val grouped = groupScheduledMeasurementsByTimeUseCase.executeInBackground(groupRequest)

        return grouped.map {
            GroupedItemsPresentationModel(
                value = it.value,
                items = it.items.map { group -> scheduledMapper.toPresentation(group) }
            )
        }
    }

    fun onAddMeasurementGroup() {
        navigateTo(ListMeasurementDestination.AddMeasurementGroup)
    }

    fun onNextScheduleTimeOut() = viewModelScope.launch {
        Log.d(TAG, "onNextScheduleTimeOut: ${currentViewState.nextSchedules.size} items")
        val retrievedList = reloadNextSchedules()
        if (retrievedList.isNotEmpty()) {
            val list = retrievedList.toMutableList()
            val selectedSchedule = list.removeFirstOrNull()

            updateViewState(
                currentViewState.copy(
                    nextSchedules = list,
                    selectedNextSchedule = selectedSchedule
                )
            )
            return@launch
        }
    }

    fun onDetailMeasurementGroup(model: MeasurementGroupPresentationModel) {
        navigateTo(ListMeasurementDestination.Detail(model.id, model.name))
    }

    fun onNextSchedule() {
        val nextSchedule = currentViewState.selectedNextSchedule
        if (nextSchedule == null) {
            notify(ListMeasurementNotification.NoNextSchedule)
            return
        }

        notify(ListMeasurementNotification.OpenNextScheduleDetail(nextSchedule))
    }

    private suspend fun reloadNextSchedules(): List<NextSchedulePresentationModel> {
        val list = currentViewState.nextSchedules

        if (list.size >= 2) {
            return list
        }

        val dateTime = if (list.size == 1) {
            list.first().dateTime.plusMinutes(1)
        } else {
            LocalDateTime.now()
        }

        return getNextScheduled(
            patientId = currentViewState.patient.id,
            dateTime = dateTime
        )
    }

    @Suppress("UnusedParameter")
    private fun handleDeleteMeasurementGroup(unit: Unit) = viewModelScope.launch {
        val retrievedList = getNextScheduled(currentViewState.patient.id).toMutableList()
        val nextSchedule = retrievedList.removeFirstOrNull()
        val direction = currentViewState.groupDirection
        val grouped = getGroupedMeasurementGroups(currentViewState.patient.id, direction)
        updateViewState(
            currentViewState.copy(
                groupedMeasurementGroups = grouped,
                nextSchedules = retrievedList,
                selectedNextSchedule = nextSchedule
            )
        )
    }

    fun schedule() = viewModelScope.launch {
        scheduleMeasurementGroupUseCase.executeInBackground("1")
    }
}
