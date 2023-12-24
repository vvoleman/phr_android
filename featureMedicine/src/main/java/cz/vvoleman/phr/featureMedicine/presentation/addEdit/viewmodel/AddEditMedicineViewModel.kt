package cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.SearchMedicineRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineScheduleByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ScheduleMedicineAlertUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory.FrequencyDaysPresentationFactory
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory.TimeUpdateFactory
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.SaveMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemPresentationModel
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
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMedicineViewState, AddEditMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditMedicineViewModel"

    @Suppress("MagicNumber")
    override fun initState(): AddEditMedicineViewState {
        val temp = listOf(LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0))
        val times = temp.map { TimePresentationModel(it, 0) }

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
                    return@execute
                }
                updateViewState(currentViewState.copy(selectedMedicine = medicineMapper.toPresentation(medicine)))
            }

            val scheduleId = savedStateHandle.get<String>(SCHEDULE_ID)
            updateViewState(currentViewState.copy(scheduleId = scheduleId))
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

        val groupedTimes = TimeUpdateFactory.groupTimes(times).toMutableList()

        groupedTimes.sortBy { it.time }

        updateViewState(currentViewState.copy(times = groupedTimes))
    }

    fun onTimeUpdate(index: Int, time: TimePresentationModel) {
        val times = currentViewState.times.toMutableList()
        times[index] = time

        val groupedTimes = TimeUpdateFactory.groupTimes(times).toMutableList()

        groupedTimes.sortBy { it.time }

        updateViewState(currentViewState.copy(times = groupedTimes))
    }

    fun onFrequencyUpdate(days: List<FrequencyDayPresentationModel>) {
        updateViewState(currentViewState.copy(frequencyDays = days))
    }

    suspend fun onSave() {
        val canSave = currentViewState.selectedMedicine != null &&
            currentViewState.times.isNotEmpty() &&
            currentViewState.frequencyDays.isNotEmpty() &&
            currentViewState.frequencyDays.any { it.isSelected }

        if (!canSave) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

        val saveMedicine = SaveMedicineSchedulePresentationModel(
            id = currentViewState.scheduleId,
            patient = currentViewState.patient!!,
            medicine = currentViewState.selectedMedicine!!,
            schedules = makeSaveSchedules(currentViewState.times, currentViewState.frequencyDays),
            createdAt = LocalDateTime.now()
        )

        if (saveMedicine.schedules.isEmpty()) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

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
                if (time.number != 0) {
                    schedules.add(
                        ScheduleItemPresentationModel(
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
        }

        return schedules
    }

    private fun handleGetMedicineScheduleById(result: MedicineScheduleDomainModel?) {
        if (result == null) {
            notify(AddEditMedicineNotification.MedicineScheduleNotFound)
            return
        }

        val itemsByDay = result.schedules.groupBy { it.dayOfWeek }
        val times = itemsByDay.values.first().map {
            TimePresentationModel(
                time = it.time,
                number = it.quantity,
            )
        }

        val frequencies = currentViewState.frequencyDaysDefault.map { day ->
            FrequencyDayPresentationModel(
                day = day.day,
                isSelected = itemsByDay.containsKey(day.day),
            )
        }

        updateViewState(
            currentViewState.copy(
                selectedMedicine = medicineMapper.toPresentation(result.medicine),
                times = times,
                frequencyDays = frequencies,
            )
        )
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
        private const val SCHEDULE_ID = "scheduleId"
    }
}
