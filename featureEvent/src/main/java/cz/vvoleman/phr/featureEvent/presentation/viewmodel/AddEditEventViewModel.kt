package cz.vvoleman.phr.featureEvent.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.domain.mapper.core.LongToReminderOffsetMapper
import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.domain.model.core.ReminderOffset
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import cz.vvoleman.phr.featureEvent.domain.usecase.addEdit.SaveEventUseCase
import cz.vvoleman.phr.featureEvent.presentation.mapper.core.EventPresentationModelToDomainMapper
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventDestination
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.ReminderPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val saveEventUseCase: SaveEventUseCase,
    private val getEventByIdRepository: GetEventByIdRepository,
    private val getSpecificMedicalWorkerRepository: GetSpecificMedicalWorkersRepository,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val eventMapper: EventPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val workerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper,
    private val reminderMapper: LongToReminderOffsetMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditEventViewState, AddEditEventNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditEventViewModel"

    override suspend fun initState(): AddEditEventViewState {
        val patient = getSelectedPatient()
        val event = getEvent()
        val workers = getSpecificMedicalWorkers(patient)
        val selectedWorker = event?.specificMedicalWorker?.let { workers.first { worker -> worker.id == it.id } }
        val reminders = getReminderOptions(event)

        return AddEditEventViewState(
            patient = patient,
            event = event,
            name = event?.name ?: "",
            description = event?.description ?: "",
            selectedWorker = selectedWorker,
            date = event?.startAt?.toLocalDate(),
            time = event?.startAt?.toLocalTime(),
            workers = workers,
            reminders = reminders,
            areRemindersEnabled = event?.reminders?.isNotEmpty() ?: false
        )
    }

    fun onReminderToggle(reminder: ReminderPresentationModel) {
        val reminders = currentViewState.reminders.map {
            if (it.id == reminder.id) {
                it.copy(isEnabled = !it.isEnabled)
            } else {
                it
            }
        }

        updateViewState(currentViewState.copy(reminders = reminders))
    }

    fun onAllRemindersToggle() {
        val newState = !currentViewState.areRemindersEnabled

        updateViewState(currentViewState.copy(areRemindersEnabled = newState))
    }

    fun onNameChanged(name: String) {
        updateViewState(currentViewState.copy(name = name))
    }

    fun onDateChanged(date: LocalDate) {
        updateViewState(currentViewState.copy(date = date))
    }

    fun onTimeChanged(time: LocalTime) {
        updateViewState(currentViewState.copy(time = time))
    }

    fun onDescriptionChanged(description: String) {
        updateViewState(currentViewState.copy(description = description))
    }

    fun onMedicalWorkerChanged(model: SpecificMedicalWorkerPresentationModel) {
        updateViewState(currentViewState.copy(selectedWorker = model))
    }

    fun onSave() = viewModelScope.launch {
        val missingFields = currentViewState.missingFields

        if (missingFields.isNotEmpty()) {
            notify(AddEditEventNotification.MissingFields(missingFields))
            return@launch
        }

        val startAt = currentViewState.date!!.atTime(currentViewState.time)
        val reminders = if (currentViewState.areRemindersEnabled) {
            currentViewState.reminders.filter { it.isEnabled }.map { it.offset }
        } else {
            emptyList()
        }

        val request = SaveEventDomainModel(
            id = currentViewState.event?.id,
            name = currentViewState.name,
            description = currentViewState.description,
            startAt = startAt,
            endAt = null,
            patientId = currentViewState.patient.id,
            specificMedicalWorkerId = currentViewState.selectedWorker?.id,
            reminders = reminderMapper.toReminderOffset(reminders)
        )

        saveEventUseCase.execute(request, ::handleSaveResult)
    }

    private fun handleSaveResult(result: EventDomainModel?) {
        if (result != null) {
            navigateTo(AddEditEventDestination.Saved(result.id))
            return
        }

        notify(AddEditEventNotification.CannotSave)
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getEvent(): EventPresentationModel? {
        val eventId = savedStateHandle.get<String>("eventId")

        requireNotNull(eventId) { "Event ID is required" }

        return getEventByIdRepository.getEventById(eventId)
            ?.let { eventMapper.toPresentation(it) }
    }

    private suspend fun getSpecificMedicalWorkers(patient: PatientPresentationModel): List<SpecificMedicalWorkerPresentationModel> {
        val workers = getSpecificMedicalWorkerRepository.getSpecificMedicalWorkers(patient.id)

        return workerMapper.toPresentation(workers)
    }

    private fun getReminderOptions(event: EventPresentationModel?): List<ReminderPresentationModel> {
        val pairs = listOf(
            Pair(R.string.reminder_one_week, ReminderOffset.OneWeek.offset),
            Pair(R.string.reminder_two_days, ReminderOffset.TwoDays.offset),
            Pair(R.string.reminder_one_day, ReminderOffset.OneDay.offset),
            Pair(R.string.reminder_two_hour, ReminderOffset.TwoHours.offset),
            Pair(R.string.reminder_one_hour, ReminderOffset.OneHour.offset),
            Pair(R.string.reminder_thirty_minutes, ReminderOffset.ThirtyMinutes.offset),
            Pair(R.string.reminder_fifteen_minutes, ReminderOffset.FifteenMinutes.offset),
            Pair(R.string.reminder_five_minutes, ReminderOffset.FiveMinutes.offset),
            Pair(R.string.reminder_one_minute, ReminderOffset.OneMinute.offset),
            Pair(R.string.reminder_at_start, ReminderOffset.AtStart.offset),
        )

        val selectedReminders = event?.reminders?.map { it.offset } ?: emptyList()

        return pairs.map { (id, offset) ->
            ReminderPresentationModel(
                id = id,
                isEnabled = selectedReminders.contains(offset),
                offset = offset
            )
        }
    }


}
