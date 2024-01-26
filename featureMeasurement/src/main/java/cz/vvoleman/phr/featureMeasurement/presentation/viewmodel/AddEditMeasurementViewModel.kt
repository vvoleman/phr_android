package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.factory.FrequencyDaysPresentationFactory
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupScheduleItemDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetUnitGroupsRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit.SaveMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit.ScheduleMeasurementGroupAlertUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupFieldPresentationToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.UnitGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMeasurementGroupUseCase: SaveMeasurementGroupUseCase,
    private val scheduleMeasurementGroupAlertUseCase: ScheduleMeasurementGroupAlertUseCase,
    private val getMeasurementGroupRepository: GetMeasurementGroupRepository,
    private val getUnitGroupsRepository: GetUnitGroupsRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    private val unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val fieldMapper: MeasurementGroupFieldPresentationToDomainMapper,
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
        val measurementGroup = getMeasurementGroup()
        val times = getTimes(measurementGroup)

        return AddEditMeasurementViewState(
            measurementGroup = measurementGroup,
            patient = patient,
            unitGroups = unitGroups,
            frequencyDaysDefault = FrequencyDaysPresentationFactory.makeDays(),
            frequencyDays = FrequencyDaysPresentationFactory.makeDays(),
            fields = measurementGroup?.fields ?: emptyList(),
            times = times,
            name = measurementGroup?.name ?: "",
        )
    }

    fun onSaveField(item: MeasurementGroupFieldPresentation) {
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
        updateViewState(currentViewState.copy(times = times.sorted().toSet()))
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

        updateViewState(currentViewState.copy(times = times.sorted().toSet()))
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

    suspend fun onSave() {
        val missingFields = currentViewState.missingFields

        if (missingFields.isNotEmpty()) {
            notify(AddEditMeasurementNotification.MissingFields(missingFields))
            return
        }

        val request = SaveMeasurementGroupDomainModel(
            id = currentViewState.measurementGroup?.id,
            name = currentViewState.name,
            patientId = currentViewState.patient.id,
            scheduleItems = makeSaveSchedules(
                times = currentViewState.times.toList(),
                frequencies = currentViewState.frequencyDays
            ),
            fields = fieldMapper.toDomain(currentViewState.fields),
        )

        saveMeasurementGroupUseCase.execute(request, ::handleSaveMeasurementGroup)

//        if (result != null) {
//            navigateTo(AddEditMeasurementDestination.SaveSuccess(id = result))
//        } else {
//            notify(AddEditMeasurementNotification.SaveError)
//        }
    }

    private fun handleSaveMeasurementGroup(result: String?) = viewModelScope.launch {
        if (result == null) {
            notify(AddEditMeasurementNotification.CannotSave)
            return@launch
        }

        val isScheduled = scheduleMeasurementGroupAlertUseCase.executeInBackground(result)

        if (!isScheduled) {
            notify(AddEditMeasurementNotification.CannotSchedule)
        }

        navigateTo(AddEditMeasurementDestination.SaveSuccess(id = result))
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

    private suspend fun getMeasurementGroup(): MeasurementGroupPresentationModel? {
        val id = savedStateHandle.get<String>("measurementGroupId") ?: return null

        return getMeasurementGroupRepository
            .getMeasurementGroup(id)
            ?.let { measurementGroupMapper.toPresentation(it) }
    }

    private suspend fun getTimes(measurementGroup: MeasurementGroupPresentationModel?): Set<LocalTime> {
        return measurementGroup?.scheduleItems
            ?.map { it.time }
            ?.toSet()
            ?: emptySet()
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
