package cz.vvoleman.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupRequest
import cz.vvoleman.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupScheduleItemDomainModel
import cz.vvoleman.featureMeasurement.domain.repository.GetUnitGroupsRepository
import cz.vvoleman.featureMeasurement.presentation.mapper.core.UnitGroupPresentationModelToDomainMapper
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel
import cz.vvoleman.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.factory.FrequencyDaysPresentationFactory
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getUnitGroupsRepository: GetUnitGroupsRepository,
    private val unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditMeasurementViewState, AddEditMeasurementNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {
    override val TAG = "AddEditMeasurementViewModel"

    override suspend fun initState(): AddEditMeasurementViewState {
        val patient = getSelectedPatient()
        val unitGroups = getUnitGroups()

        return AddEditMeasurementViewState(
            patient = patient,
            unitGroups = unitGroups,
            frequencyDaysDefault = FrequencyDaysPresentationFactory.makeDays(),
            frequencyDays = FrequencyDaysPresentationFactory.makeDays(),
            fields = listOf(
                NumericFieldPresentationModel("1", "Hmotnost", null, null, null),
            ),
        )
    }

    fun onSaveField(item: MeasurementGroupFieldPresentation) {
        // If there is already item in list with same id, replace it
        // If not, add new item to list
        val fields = currentViewState.fields.toMutableList()
        val index = fields.indexOfFirst { it.id == item.id }

        if (index != -1) {
            fields[index] = item
        } else {
            fields.add(item)
        }

        updateViewState(currentViewState.copy(fields = fields))
    }

    fun onDeleteField(item: MeasurementGroupFieldPresentation) {
        val fields = currentViewState.fields.toMutableList()
        fields.remove(item)

        updateViewState(currentViewState.copy(fields = fields))
    }

    fun onAddTime(time: LocalTime?) {
        val times = currentViewState.times.toMutableSet()
        times.add(time ?: return)
        updateViewState(currentViewState.copy(times = times))
    }

    fun onFrequencyUpdate(days: List<FrequencyDayPresentationModel>) {
        updateViewState(currentViewState.copy(frequencyDays = days))
    }

    fun onGetTime(i: Int): LocalTime? {
        return currentViewState.times.elementAtOrNull(i)
    }

    fun onTimeUpdate(index: Int, updatedTime: LocalTime) {
        val times = currentViewState.times.toMutableSet()
        val oldTime = times.elementAtOrNull(index) ?: return
        times.remove(oldTime)
        times.add(updatedTime)

        updateViewState(currentViewState.copy(times = times))
    }

    fun onTimeDelete(index: Int) {
        val times = currentViewState.times.toMutableSet()
        val time = times.elementAtOrNull(index) ?: return
        times.remove(time)

        updateViewState(currentViewState.copy(times = times))
    }

    fun onNameUpdate(name: String) {
        updateViewState(currentViewState.copy(name = name))
    }

    fun onSave() {
        val missingFields = currentViewState.missingFields

        if (missingFields.isNotEmpty()) {
            notify(AddEditMeasurementNotification.MissingFields(missingFields))
            return
        }

        val request = SaveMeasurementGroupRequest(
            id = currentViewState.measurementGroup?.id,
            name = currentViewState.name,
            patientId = currentViewState.patient.id,
            scheduleItems = listOf(),
            fields = listOf()
        )

    }

    private fun makeSaveSchedules(
        times: List<LocalTime>,
        frequencies: List<FrequencyDayPresentationModel>
    ): List<SaveMeasurementGroupScheduleItemDomainModel> {
        val schedules = mutableListOf<SaveMeasurementGroupScheduleItemDomainModel>()

        for (time in times) {
            for (frequency in frequencies) {
                if (frequency.isSelected) {
                    schedules.add(
                        SaveMeasurementGroupScheduleItemDomainModel(
                            time = time,
                            dayOfWeek = frequency.day,
                            scheduledAt = LocalDateTime.now(),
                        )
                    )
                }
            }
        }

        return schedules
    }

    private suspend fun getUnitGroups(): List<UnitGroupPresentationModel> {
        return getUnitGroupsRepository.getUnitGroups()
            .map { unitGroupMapper.toPresentation(it) }
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()

        return patientMapper.toPresentation(patient)
    }
}
