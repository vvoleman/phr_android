package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.facade.MedicineScheduleFacade
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetNextScheduledUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.NextScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.ScheduleItemWithDetailsPresentationModelToDomainMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
@Suppress("UnusedPrivateProperty")
class ListMedicineViewModel @Inject constructor(
    private val getNextScheduledUseCase: GetNextScheduledUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val scheduleItemDetailsMapper: ScheduleItemWithDetailsPresentationModelToDomainMapper,
    private val scheduleItemMapper: ScheduleItemPresentationModelToDomainMapper,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
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

        Log.d(TAG, "onCreate: ")

        viewModelScope.launch {
            loadSelectedPatient()

            if (currentViewState.patient == null) {
                notify(ListMedicineNotification.UnableToLoad)
                return@launch
            }

            val nextRequest = NextScheduledRequestDomainModel(
                patientId = currentViewState.patient!!.id,
            )
            getNextScheduledUseCase.execute(nextRequest, ::handleGetNextSchedule)

        }
    }

    fun onCreate() {
        super.onInit()
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

    fun onEdit(id: String) {
        navigateTo(ListMedicineDestination.EditSchedule(id))
    }

    private fun handleGetNextSchedule(result: List<ScheduleItemWithDetailsDomainModel>) {
        val schedules = result.map { scheduleItemDetailsMapper.toPresentation(it) }

        Log.d(TAG, "nextSchedule size: ${result.size}")

        var selectedSchedule: NextScheduleItemPresentationModel? = null;
        if (result.isNotEmpty()) {
            val next = MedicineScheduleFacade.getNextScheduleItem(result, LocalDateTime.now())

            selectedSchedule = NextScheduleItemPresentationModel(
                scheduleItems = next.map { scheduleItemDetailsMapper.toPresentation(it) },
                dateTime = next.first().scheduleItem.getTranslatedDateTime(LocalDateTime.now())
            )
        }

        updateViewState(
            currentViewState.copy(
                nextSchedules = schedules,
                selectedNextSchedule = selectedSchedule
            )
        )
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    companion object {
        const val TAG = "ListMedicineViewModel"
    }

}
