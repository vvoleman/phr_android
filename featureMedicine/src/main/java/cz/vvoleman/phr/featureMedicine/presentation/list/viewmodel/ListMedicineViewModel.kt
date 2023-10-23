package cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.common.data.alarm.AlarmReceiver
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.data.alarm.TestContent
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmContent
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmReceiver
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
@Suppress("UnusedPrivateProperty")
class ListMedicineViewModel @Inject constructor(
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val alarmScheduler: AlarmScheduler,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicineViewState, ListMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMedicineViewModel"

    override fun initState(): ListMedicineViewState {
        return ListMedicineViewState()
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

    fun onEdit(id: String) {
        navigateTo(ListMedicineDestination.EditSchedule(id))
    }
}
