package cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.factory.FrequencyDaysPresentationFactory
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicinesPagingStreamRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineScheduleByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ScheduleMedicineAlertUseCase
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory.TimeUpdateFactory
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineDestination
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.SaveMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditMedicineViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicineScheduleUseCase: SaveMedicineScheduleUseCase,
    private val getMedicineScheduleByIdUseCase: GetMedicineScheduleByIdUseCase,
    private val scheduleMedicineAlertUseCase: ScheduleMedicineAlertUseCase,
    private val getMedicinesPagingStreamRepository: GetMedicinesPagingStreamRepository,
    private val getProblemCategoriesRepository: GetProblemCategoriesRepository,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val saveMedicineMapper: SaveMedicineSchedulePresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val scheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMedicineViewState, AddEditMedicineNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditMedicineViewModel"

    @Suppress("MagicNumber")
    override suspend fun initState(): AddEditMedicineViewState {
        val patient = getSelectedPatient()
        val schedule = getSchedule()
        val availableProblemCategories = getAvailableProblemCategories(patient)
        val timesFrequencies = getTimesAndFrequencies(schedule)

        return AddEditMedicineViewState(
            selectedMedicine = schedule?.medicine,
            patient = patient,
            availableProblemCategories = availableProblemCategories,
            times = timesFrequencies.first,
            frequencyDays = timesFrequencies.second,
            frequencyDaysDefault = FrequencyDaysPresentationFactory.makeDays(),
            problemCategory = schedule?.problemCategory,
        )
    }

    override suspend fun onInit() {
        super.onInit()
        viewModelScope.launch {
            val scheduleId = savedStateHandle.get<String>(SCHEDULE_ID)
            updateViewState(currentViewState.copy(scheduleId = scheduleId))
        }
    }

    fun onMedicineSearch(query: String): Flow<PagingData<MedicinePresentationModel>>{
        if (query != currentViewState.medicineQuery || currentViewState.medicineStream == null) {
            updateViewState(currentViewState.copy(medicineQuery = query))

            val flow = getMedicinesPagingStreamRepository
                .getMedicinesPagingStream(query)
                .map { pagingData ->
                    pagingData.map {
                        medicineMapper.toPresentation(it)
                    }
                }
                .cachedIn(viewModelScope)

            updateViewState(
                currentViewState.copy(
                    medicineStream = flow
                )
            )

            return flow
        }

        return currentViewState.medicineStream ?: throw IllegalStateException("Medicine stream is null")
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

    fun canAddTime(): Boolean {
        return currentViewState.times.size < MAX_TIMES
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
            createdAt = LocalDateTime.now(),
            problemCategory = currentViewState.problemCategory,
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

    fun onProblemCategorySelected(value: String?) {
        val problemCategory = currentViewState.availableProblemCategories.find { it.name == value }
        updateViewState(currentViewState.copy(problemCategory = problemCategory))
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
                if (time.number.toDouble() != 0.0) {
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

    private fun getTimesAndFrequencies(
        schedule: MedicineSchedulePresentationModel?
    ): Pair<List<TimePresentationModel>, List<FrequencyDayPresentationModel>> {

        val times = if (schedule != null) {
            val itemsByDay = schedule.schedules.groupBy { it.dayOfWeek }
            itemsByDay.values.first().map {
                TimePresentationModel(
                    time = it.time,
                    number = it.quantity,
                )
            }
        } else {
            val temp = listOf(LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0))
            temp.map { TimePresentationModel(it, 0) }
        }

        val defaultFrequencies = FrequencyDaysPresentationFactory.makeDays()
        val frequencies = if (schedule != null) {
            val itemsByDay = schedule.schedules.groupBy { it.dayOfWeek }
            defaultFrequencies.map { day ->
                FrequencyDayPresentationModel(
                    day = day.day,
                    isSelected = itemsByDay.containsKey(day.day),
                )
            }
        } else {
            defaultFrequencies
        }

        return Pair(times, frequencies)
    }
    private fun handleSaveMedicineSchedule(result: String?) {
        if (result == null) {
            notify(AddEditMedicineNotification.CannotSave)
            return
        }

        viewModelScope.launch {
            scheduleMedicineAlertUseCase.execute(result) { isScheduled ->
                if (!isScheduled) {
                    notify(AddEditMedicineNotification.CannotSchedule)
                    return@execute
                }
            }
        }
        navigateTo(AddEditMedicineDestination.MedicineSaved(result))
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()

        return patientMapper.toPresentation(patient)
    }

    private suspend fun getSchedule(): MedicineSchedulePresentationModel? {
        val scheduleId = savedStateHandle.get<String>(SCHEDULE_ID) ?: return null

        val schedule = getMedicineScheduleByIdUseCase.executeInBackground(scheduleId)

        return schedule?.let { scheduleMapper.toPresentation(it) }
    }

    private suspend fun getAvailableProblemCategories(
        patient: PatientPresentationModel
    ): List<ProblemCategoryPresentationModel> {
        return getProblemCategoriesRepository.getProblemCategories(patient.id).map {
            problemCategoryMapper.toPresentation(it)
        }
    }

    companion object {
        private const val SCHEDULE_ID = "scheduleId"
        private const val MAX_TIMES = 5
    }
}
