package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.grouped.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.domain.facade.MedicineScheduleFacade
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupScheduleItemsRequest
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequest
import cz.vvoleman.phr.featureMedicine.domain.usecase.DeleteMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetNextScheduledUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupScheduleItemsUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsDomainModelToNextScheduleMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemWithDetailsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val scheduleItemDetailsMapper: ScheduleItemWithDetailsPresentationModelToDomainMapper,
    private val medicineScheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    private val nextScheduleMapper: ScheduleItemWithDetailsDomainModelToNextScheduleMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override suspend fun initState(): ListMedicineViewState {
        return ListMedicineViewState()
    }

    override suspend fun onInit() {
        super.onInit()

        viewModelScope.launch {
            loadSelectedPatient()

            if (currentViewState.patient == null) {
                notify(ListMedicineNotification.UnableToLoad)
                return@launch
            }

            retrieveSchedules()
        }
    }

    fun onCreate() {
//        val time = LocalTime.now().plusSeconds(5)
//
//        // Create schedule
//        val result = alarmScheduler.schedule(AlarmItem(
//            id = "medicine-schedule-${time.toString()}",
//            triggerAt = time,
//            content = MedicineAlarmContent(
//                medicineScheduleId = "1",
//                triggerAt = time.toSecondOfDay().toLong(),
//                alarmDays = listOf(DayOfWeek.SUNDAY)
//            ),
// //            content = TestContent(id = "1"),
//            repeatInterval = AlarmItem.REPEAT_SECOND.toLong()*10,
//            receiver = MedicineAlarmReceiver::class.java
//        ))
//
//        Log.d(TAG, "Was scheduled?: $result")
        navigateTo(ListMedicineDestination.CreateSchedule)
    }

    fun onDelete(id: String) = viewModelScope.launch {
        deleteMedicineScheduleUseCase.execute(id) {
            val isDeleted = it.isScheduleDeleted && it.isAlarmDeleted

            if (isDeleted) {
                notify(ListMedicineNotification.Deleted)
                retrieveSchedules()
            } else if (it.isScheduleDeleted) {
                notify(ListMedicineNotification.AlarmNotDeleted)
                retrieveSchedules()
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

    fun onNextScheduleTimeOut() {
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
            return
        }

        loadNextSchedules()
    }

    private fun retrieveSchedules() = viewModelScope.launch {
        val today = LocalDate.now()
        val rangeRequest = SchedulesInRangeRequest(
            patientId = currentViewState.patient!!.id,
            startAt = today.atTime(LocalTime.now()),
            endAt = today.atTime(LocalTime.MAX)
        )
        getScheduledInTimeRangeUseCase.execute(rangeRequest, ::handleGroupScheduleItems)
        groupMedicineSchedulesUseCase.execute(currentViewState.patient!!.id, ::handleGroupMedicineSchedules)

        loadNextSchedules()
    }

    private fun loadNextSchedules() = viewModelScope.launch {
        val nextRequest = NextScheduledRequestDomainModel(
            patientId = currentViewState.patient!!.id,
        )
        getNextScheduledUseCase.execute(nextRequest, ::handleGetNextSchedule)
    }

    private fun handleGetNextSchedule(result: List<ScheduleItemWithDetailsDomainModel>) {
        val schedules = result.map { scheduleItemDetailsMapper.toPresentation(it) }
        val selectedSchedule = getNextSelectedSchedule(schedules)

        updateViewState(
            currentViewState.copy(
                nextSchedules = schedules,
                selectedNextSchedule = selectedSchedule
            )
        )
    }

    private fun handleGroupScheduleItems(result: List<ScheduleItemWithDetailsDomainModel>) = viewModelScope.launch {
        val request = GroupScheduleItemsRequest(
            scheduleItems = result,
            currentDateTime = LocalDateTime.now()
        )

        groupScheduleItemsUseCase.execute(request) { listGrouped ->
            val list = listGrouped.map { group ->
                val items = group.items.map { scheduleItemDetailsMapper.toPresentation(it) }
                GroupedItemsPresentationModel(
                    group.value,
                    items
                )
            }

            updateViewState(currentViewState.copy(timelineSchedules = list))
        }
    }

    private fun handleGroupMedicineSchedules(result: List<GroupedItemsDomainModel<MedicineScheduleDomainModel>>) {
        val list = result.map { group ->
            val items = group.items.map { medicineScheduleMapper.toPresentation(it) }
            GroupedItemsPresentationModel(
                group.value,
                items
            )
        }

        updateViewState(currentViewState.copy(medicineCatalogue = list))
    }

    private fun getNextSelectedSchedule(
        list: List<ScheduleItemWithDetailsPresentationModel>
    ): NextSchedulePresentationModel? {
        var selectedSchedule: NextSchedulePresentationModel? = null
        if (list.isNotEmpty()) {
            val listDomain = list.map { scheduleItemDetailsMapper.toDomain(it) }
            val next = MedicineScheduleFacade.getNextScheduleItem(listDomain, LocalDateTime.now())

            selectedSchedule = nextScheduleMapper.toNextSchedule(next)
        }

        return selectedSchedule
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    fun onNextScheduleClick(model: NextSchedulePresentationModel) {
        // Retrieve schedule items by ids
        if (model.id != currentViewState.selectedNextSchedule?.id || model.items.isEmpty()) {
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

    companion object {
        const val TAG = "ListMedicineViewModel"
    }
}
