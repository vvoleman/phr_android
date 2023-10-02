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
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineScheduleByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.SaveMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.SaveScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel
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
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMedicineViewState, AddEditMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditMedicineViewModel"

    @Suppress("MagicNumber")
    override fun initState(): AddEditMedicineViewState {
        val temp = listOf(LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0))
        val times = temp.map { TimePresentationModel(null, it, 0) }
        return AddEditMedicineViewState(
            times = times
        )
    }

    override fun onInit() {
        super.onInit()
        viewModelScope.launch {
            loadSelectedPatient()

            val scheduleId = savedStateHandle.get<String>(SCHEDULE_ID)
            if (scheduleId != null) {
                getMedicineScheduleByIdUseCase.execute(scheduleId, ::handleGetMedicineScheduleById)
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

    fun onSave() {
        Log.d(TAG, "selectedMedicine: ${currentViewState.selectedMedicine}")
        Log.d(TAG, "times: ${currentViewState.times}")
        Log.d(TAG, "frequencyDays: ${currentViewState.frequencyDays}")
        val canSave = currentViewState.selectedMedicine != null &&
                currentViewState.times.isNotEmpty() && currentViewState.frequencyDays.isNotEmpty()

        if (!canSave) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

        Log.d(TAG, "onSave: ${currentViewState.selectedMedicine}")
        val saveMedicine = SaveMedicineSchedulePresentationModel(
            patient = currentViewState.patient!!,
            medicine = currentViewState.selectedMedicine!!,
            schedules = makeSaveSchedules(currentViewState.times, currentViewState.frequencyDays),
            createdAt = LocalDateTime.now()
        )
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    private fun makeSaveSchedules(
        times: List<TimePresentationModel>,
        frequencies: List<FrequencyDayPresentationModel>
    ): List<SaveScheduleItemPresentationModel> {
        val schedules = mutableListOf<SaveScheduleItemPresentationModel>()

        frequencies.forEach { frequency ->
            if (!frequency.isSelected) {
                return@forEach
            }
            times.forEach { time ->
                schedules.add(SaveScheduleItemPresentationModel(
                    id = time.id,
                    dayOfWeek = frequency.day,
                    time = time.time,
                    scheduledAt = LocalDateTime.now(),
                    endingAt = null,
                    quantity = time.number,
                    unit = "",
                ))
            }
        }

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

    companion object {
        private const val TAG = "AddEditMedicineViewModel"
        private const val SCHEDULE_ID = "scheduleId"
    }
}
