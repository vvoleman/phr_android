package cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.SearchMedicineRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineScheduleByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ScheduleMedicineAlertUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory.FrequencyDaysPresentationFactory
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.addEdit.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.SaveMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditMedicineViewModel @Inject constructor(
    private val searchMedicineUseCase: SearchMedicineUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicineScheduleUseCase: SaveMedicineScheduleUseCase,
    private val getMedicineScheduleByIdUseCase: GetMedicineScheduleByIdUseCase,
    private val getMedicineByIdUseCase: GetMedicineByIdUseCase,
    private val scheduleMedicineAlertUseCase: ScheduleMedicineAlertUseCase,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val saveMedicineMapper: SaveMedicineSchedulePresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMedicineViewState, AddEditMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditMedicineViewModel"

    @Suppress("MagicNumber")
    override fun initState(): AddEditMedicineViewState {
        val temp = listOf(LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0))
        val times = temp.map { TimePresentationModel(null, it, 0) }
        Log.d(TAG, "a")

        return AddEditMedicineViewState(
            times = times,
            frequencyDaysDefault = FrequencyDaysPresentationFactory.makeDays(),
        )
    }

    override fun onInit() {
        super.onInit()
        Log.d(TAG, "b")
        viewModelScope.launch {
            loadSelectedPatient()

            // Delete later
            getMedicineByIdUseCase.execute("0000009") { medicine ->
                if (medicine == null) {
                    Log.d(TAG, "onInit: medicine is null")
                    return@execute
                }
                updateViewState(currentViewState.copy(selectedMedicine = medicineMapper.toPresentation(medicine)))

                Log.d(TAG, "new medicine is: ${currentViewState.selectedMedicine}")
            }

            val scheduleId = savedStateHandle.get<String>(SCHEDULE_ID)
            if (scheduleId != null) {
                getMedicineScheduleByIdUseCase.execute(scheduleId, ::handleGetMedicineScheduleById)
            } else {
                updateViewState(currentViewState.copy(frequencyDays = FrequencyDaysPresentationFactory.makeDays()))
            }
        }
    }

    fun onSearch(query: String) = viewModelScope.launch {
        val request = SearchMedicineRequestDomainModel(query)
        searchMedicineUseCase.execute(request) { list ->
            updateViewState(currentViewState.copy(medicines = list.map { medicineMapper.toPresentation(it) }))
        }
    }

    fun onMedicineSelected(medicine: MedicinePresentationModel?) {
        updateViewState(currentViewState.copy(selectedMedicine = medicine))
    }

    fun onTimeDelete(index: Int) {
        val times = currentViewState.times.toMutableList()
        times.removeAt(index)
        updateViewState(currentViewState.copy(times = times))
    }

    fun onGetTime(index: Int): TimePresentationModel {
        return currentViewState.times[index]
    }

    fun onTimeAdd(time: TimePresentationModel) {
        val times = currentViewState.times.toMutableList()
        times.add(time)

        times.sortBy { it.time }

        updateViewState(currentViewState.copy(times = times))
    }

    fun onTimeUpdate(index: Int, time: TimePresentationModel) {
        val times = currentViewState.times.toMutableList()
        times[index] = time

        times.sortBy { it.time }

        updateViewState(currentViewState.copy(times = times))
    }

    fun onFrequencyUpdate(days: List<FrequencyDayPresentationModel>) {
        updateViewState(currentViewState.copy(frequencyDays = days))
    }

    suspend fun onSave() {
        Log.d(TAG, "selectedMedicine: ${currentViewState.selectedMedicine}")
        Log.d(TAG, "times: ${currentViewState.times}")
        Log.d(TAG, "frequencyDays: ${currentViewState.frequencyDays}")
        val canSave = currentViewState.selectedMedicine != null &&
                currentViewState.times.isNotEmpty() && currentViewState.frequencyDays.isNotEmpty()

        if (!canSave) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

        val saveMedicine = SaveMedicineSchedulePresentationModel(
            patient = currentViewState.patient!!,
            medicine = currentViewState.selectedMedicine!!,
            schedules = makeSaveSchedules(currentViewState.times, currentViewState.frequencyDays),
            createdAt = LocalDateTime.now()
        )

        val times = currentViewState.times
        val frequencies = currentViewState.frequencyDays
        Log.d(TAG, "number of times: ${times.size}")
        Log.d(TAG, "number of frequencies: ${frequencies.size}")
        Log.d(TAG, "number of items to be saved: ${saveMedicine.schedules.size}")

        val domainModel = saveMedicineMapper.toDomain(saveMedicine)
        saveMedicineScheduleUseCase.execute(domainModel, ::handleSaveMedicineSchedule)
    }

    fun onQuantityChange(index: Int, newValue: Number) {
        val times = currentViewState.times.toMutableList()
        times[index] = times[index].copy(number = newValue)
        updateViewState(currentViewState.copy(times = times))
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    private fun makeSaveSchedules(
        times: List<TimePresentationModel>,
        frequencies: List<FrequencyDayPresentationModel>
    ): List<ScheduleItemPresentationModel> {
        val schedules = mutableListOf<ScheduleItemPresentationModel>()

        frequencies.forEach { frequency ->
            if (!frequency.isSelected) {
                return@forEach
            }
            times.forEach { time ->
                if (time.number == 0) {
                    return@forEach
                }
                schedules.add(
                    ScheduleItemPresentationModel(
                        id = time.id,
                        dayOfWeek = frequency.day,
                        time = time.time,
                        scheduledAt = LocalDateTime.now(),
                        endingAt = null,
                        quantity = time.number,
                        unit = "",
                    )
                )
            }
        }

        Log.d(TAG, "numberOfSchedules: ${schedules.size}")
        return schedules
    }

    private fun handleGetMedicineScheduleById(result: MedicineScheduleDomainModel?) {
        if (result == null) {
            notify(AddEditMedicineNotification.MedicineScheduleNotFound)
            return
        }

        updateViewState(currentViewState.copy(
            selectedMedicine = medicineMapper.toPresentation(result.medicine),
            times = result.schedules.map { TimePresentationModel(it.id, it.time, it.quantity) }
        ))
    }
    private fun handleSaveMedicineSchedule(result: String?) {
        if (result == null) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

        viewModelScope.launch {
            scheduleMedicineAlertUseCase.execute(result) { isScheduled ->
                if (!isScheduled) {
                    notify(AddEditMedicineNotification.CannotScheduleMedicine)
                    return@execute
                }

                Log.d(TAG, "alert for schedule \"$result\" scheduled")
            }
        }
        navigateTo(AddEditMedicineDestination.MedicineSaved(result))
    }

    companion object {
        private const val TAG = "AddEditMedicineViewModel"
        private const val SCHEDULE_ID = "scheduleId"
    }
}
