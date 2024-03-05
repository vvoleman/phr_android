package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.grouped.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.domain.facade.MedicineScheduleFacade
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupScheduleItemsRequest
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequest
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.ToggleScheduleAlarmRequest
import cz.vvoleman.phr.featureMedicine.domain.repository.MarkMedicineScheduleFinishedRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.DeleteMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetNextScheduledUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupScheduleItemsUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ToggleScheduleAlarmUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsDomainModelToNextScheduleMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemWithDetailsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
@Suppress("UnusedPrivateProperty")
class ListMedicineViewModel @Inject constructor(
    private val getNextScheduledUseCase: GetNextScheduledUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getScheduledInTimeRangeUseCase: GetScheduledInTimeRangeUseCase,
    private val groupScheduleItemsUseCase: GroupScheduleItemsUseCase,
    private val groupMedicineSchedulesUseCase: GroupMedicineScheduleUseCase,
    private val deleteMedicineScheduleUseCase: DeleteMedicineScheduleUseCase,
    private val toggleScheduleAlarmUseCase: ToggleScheduleAlarmUseCase,
    private val markMedicineScheduleFinishedRepository: MarkMedicineScheduleFinishedRepository,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val scheduleItemDetailsMapper: ScheduleItemWithDetailsPresentationModelToDomainMapper,
    private val medicineScheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    private val nextScheduleMapper: ScheduleItemWithDetailsDomainModelToNextScheduleMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override suspend fun initState(): ListMedicineViewState {
        val patient = getSelectedPatient()
        val nextSchedules = getNextSchedules(patient)
        val selectedSchedule = getNextSelectedSchedule(nextSchedules)
        val timelineSchedules = getTimelineSchedules(patient)
        val medicineCatalogue = getMedicineCatalogue(patient)

        return ListMedicineViewState(
            patient = patient,
            nextSchedules = nextSchedules,
            selectedNextSchedule = selectedSchedule,
            timelineSchedules = timelineSchedules,
            medicineCatalogue = medicineCatalogue
        )
    }

    fun onCreate() {
        navigateTo(ListMedicineDestination.CreateSchedule)
    }

    fun onDelete(id: String) = viewModelScope.launch {
        deleteMedicineScheduleUseCase.execute(id) {
            val isDeleted = it.isScheduleDeleted && it.isAlarmDeleted

            if (isDeleted) {
                notify(ListMedicineNotification.Deleted)
                reloadSchedules()
            } else if (it.isScheduleDeleted) {
                notify(ListMedicineNotification.AlarmNotDeleted)
                reloadSchedules()
            } else {
                notify(ListMedicineNotification.ScheduleNotDeleted)
            }
        }
    }

    fun onExportSelected() {
        navigateTo(ListMedicineDestination.Export)
    }

    fun onEdit(id: String) {
        navigateTo(ListMedicineDestination.EditSchedule(id))
    }

    fun onStopScheduling(item: MedicineSchedulePresentationModel) = viewModelScope.launch  {
        val model = medicineScheduleMapper.toDomain(item)

        markMedicineScheduleFinishedRepository.markMedicineScheduleFinished(model, LocalDateTime.now())
        reloadSchedules()
    }

    fun onNextScheduleTimeOut() = viewModelScope.launch {
        Log.d(TAG, "onNextScheduleTimeOut: ${currentViewState.nextSchedules.size} items")
        if (currentViewState.nextSchedules.size > 1) {
            val list = currentViewState.nextSchedules.toMutableList()
            list.removeFirst()
            val selectedSchedule = getNextSelectedSchedule(list)

            updateViewState(
                currentViewState.copy(
                    nextSchedules = list,
                    selectedNextSchedule = selectedSchedule
                )
            )
            return@launch
        }

        reloadSchedules()
    }

    fun onAlarmToggle(model: ScheduleItemWithDetailsPresentationModel, oldState: Boolean) = viewModelScope.launch {
        val request = ToggleScheduleAlarmRequest(
            schedule = scheduleItemDetailsMapper.toDomain(model),
            newState = !oldState
        )

        val result = toggleScheduleAlarmUseCase.executeInBackground(request)

        if (!result) {
            notify(ListMedicineNotification.UnableToToggleAlarm)
            return@launch
        }

        val items = currentViewState.timelineSchedules.map {
            // Check if items have medicine same as model
            val newItems = it.items.map { item ->
                if (item.medicine.id == model.medicine.id) {
                    item.copy(isAlarmEnabled = !oldState)
                } else {
                    item
                }
            }

            it.copy(items = newItems)
        }

        val nextSchedules = getNextSchedules(currentViewState.patient!!)
        val selectedSchedule = getNextSelectedSchedule(nextSchedules)
        updateViewState(
            currentViewState.copy(
                timelineSchedules = items,
                selectedNextSchedule = selectedSchedule,
                nextSchedules = nextSchedules
            )
        )
    }

    fun onNextScheduleClick(model: NextSchedulePresentationModel) {
        // Retrieve schedule items by ids
        if (model.dateTime != currentViewState.selectedNextSchedule?.dateTime || model.items.isEmpty()) {
            return
        }

        val ids = model.items.map { it.id }
        val list = currentViewState.nextSchedules.toMutableList()

        val items = mutableListOf<ScheduleItemWithDetailsPresentationModel>()
        ids.forEach { id ->
            val item = list.find { it.scheduleItem.id == id }
            if (item != null) {
                items.add(item)
            }
        }

        notify(
            ListMedicineNotification.OpenSchedule(
                dateTime = model.items.first().time,
                items = items.sortedBy { it.medicine.name }
            )
        )
    }

    private fun reloadSchedules() = viewModelScope.launch {
        val timeline = getTimelineSchedules(currentViewState.patient!!)
        val medicineCatalogue = getMedicineCatalogue(currentViewState.patient!!)
        val nextSchedules = getNextSchedules(currentViewState.patient!!)
        val selectedSchedule = getNextSelectedSchedule(nextSchedules)

        updateViewState(
            currentViewState.copy(
                timelineSchedules = timeline,
                medicineCatalogue = medicineCatalogue,
                nextSchedules = nextSchedules,
                selectedNextSchedule = selectedSchedule
            )
        )
    }

    private fun getNextSelectedSchedule(
        list: List<ScheduleItemWithDetailsPresentationModel>
    ): NextSchedulePresentationModel? {
        var selectedSchedule: NextSchedulePresentationModel? = null
        val listDomain = list.filter { it.isAlarmEnabled }.map { scheduleItemDetailsMapper.toDomain(it) }
        if (listDomain.isNotEmpty()) {
            val next = MedicineScheduleFacade.getNextScheduleItem(listDomain, LocalDateTime.now())

            selectedSchedule = nextScheduleMapper.toNextSchedule(next)
        }

        return selectedSchedule
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).firstOrNull()
        if (patient == null) {
            notify(ListMedicineNotification.UnableToLoad)
            throw IllegalStateException("Patient not found")
        }

        return patientMapper.toPresentation(patient)
    }

    private suspend fun getNextSchedules(patient: PatientPresentationModel): List<ScheduleItemWithDetailsPresentationModel> {
        val nextRequest = NextScheduledRequestDomainModel(
            patientId = patient.id
        )

        val results = getNextScheduledUseCase.executeInBackground(nextRequest)

        return results.map { scheduleItemDetailsMapper.toPresentation(it) }
    }

    private suspend fun getTimelineSchedules(patient: PatientPresentationModel): List<GroupedItemsPresentationModel<ScheduleItemWithDetailsPresentationModel>> {
        val today = LocalDate.now()
        val rangeRequest = SchedulesInRangeRequest(
            patientId = patient.id,
            startAt = today.atTime(LocalTime.MIN),
            endAt = today.atTime(LocalTime.MAX)
        )

        val results = getScheduledInTimeRangeUseCase.executeInBackground(rangeRequest)

        val request = GroupScheduleItemsRequest(
            scheduleItems = results,
            currentDateTime = LocalDateTime.now()
        )

        val listGrouped = groupScheduleItemsUseCase.executeInBackground(request)

        return listGrouped.map { group ->
            val items = group.items.map { scheduleItemDetailsMapper.toPresentation(it) }
            GroupedItemsPresentationModel(
                group.value,
                items
            )
        }
    }

    private suspend fun getMedicineCatalogue(patient: PatientPresentationModel): List<GroupedItemsPresentationModel<MedicineSchedulePresentationModel>> {
        val results = groupMedicineSchedulesUseCase.executeInBackground(patient.id)

        return results.map { group ->
            val items = group.items.map { medicineScheduleMapper.toPresentation(it) }
            GroupedItemsPresentationModel(
                group.value,
                items
            )
        }
    }

    companion object {
        const val TAG = "ListMedicineViewModel"
    }
}
