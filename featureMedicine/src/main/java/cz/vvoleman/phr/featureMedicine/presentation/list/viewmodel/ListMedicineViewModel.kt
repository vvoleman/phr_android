package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.GroupedItemsPresentationModel
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
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.NextScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsPresentationModelToDomainMapper
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
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val deleteMedicineScheduleUseCase: DeleteMedicineScheduleUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val scheduleItemDetailsMapper: ScheduleItemWithDetailsPresentationModelToDomainMapper,
    private val scheduleItemMapper: ScheduleItemPresentationModelToDomainMapper,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val medicineScheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    private val alarmScheduler: AlarmScheduler,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override fun initState(): ListMedicineViewState {
        return ListMedicineViewState(
        )
    }

    override fun onInit() {
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
////            content = TestContent(id = "1"),
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

    private fun getNextSelectedSchedule(list: List<ScheduleItemWithDetailsPresentationModel>): NextScheduleItemPresentationModel? {
        var selectedSchedule: NextScheduleItemPresentationModel? = null
        if (list.isNotEmpty()) {
            val listDomain = list.map { scheduleItemDetailsMapper.toDomain(it) }
            val next = MedicineScheduleFacade.getNextScheduleItem(listDomain, LocalDateTime.now())

            selectedSchedule = NextScheduleItemPresentationModel(
                scheduleItems = next.map { scheduleItemDetailsMapper.toPresentation(it) },
                dateTime = next.first().scheduleItem.getTranslatedDateTime(LocalDateTime.now())
            )
        }

        return selectedSchedule
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    companion object {
        const val TAG = "ListMedicineViewModel"
    }

}
