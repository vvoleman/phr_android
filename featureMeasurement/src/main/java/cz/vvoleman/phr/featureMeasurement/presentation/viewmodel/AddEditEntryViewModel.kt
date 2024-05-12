package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.GetEntryFieldsRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.SaveEntryRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEditEntry.GetEntryFieldsUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEditEntry.SaveEntryUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.addEditEntry.EntryFieldPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupEntryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.EntryFieldPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.NavigationSource
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupScheduleItemPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditEntryViewModel @Inject constructor(
    private val getEntryFieldsUseCase: GetEntryFieldsUseCase,
    private val saveEntryUseCase: SaveEntryUseCase,
    private val getMeasurementGroupRepository: GetMeasurementGroupRepository,
    private val entryFieldMapper: EntryFieldPresentationModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryPresentationModelToDomainMapper,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditEntryViewState, AddEditEntryNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditEntryViewModel"

    override suspend fun initState(): AddEditEntryViewState {
        val measurementGroup = getMeasurementGroup()
        val existingEntry = getExistingEntry(measurementGroup)
        val entryFields = getEntryFields(measurementGroup, existingEntry)
        val scheduleItem = getScheduleItem(measurementGroup)
        val navigationSource = savedStateHandle.get<NavigationSource>("source")
        require(navigationSource != null) { "Navigation source is null" }

        return AddEditEntryViewState(
            measurementGroup = measurementGroup,
            existingEntry = existingEntry,
            entryFields = entryFields,
            scheduleItemId = scheduleItem?.id,
            dateTime = existingEntry?.createdAt ?: LocalDateTime.now(),
            navigationSource = navigationSource
        )
    }

    fun onEntryFieldValueChange(id: String, value: String) {
        val entries = currentViewState.entryFields.toMutableMap()

        if (!entries.containsKey(id)) {
            Log.e(TAG, "Unable to find entry with ID#$id")
            return
        }

        entries[id] = entries[id]!!.copy(value = value)

        updateViewState(currentViewState.copy(entryFields = entries))
    }

    fun onSave() = viewModelScope.launch {
        val errorFields = currentViewState.errorFields

        if (errorFields.isNotEmpty()) {
            Log.d(TAG, "Unable to save entry, error fields: $errorFields")
            notify(AddEditEntryNotification.FieldErrors(errorFields))
            return@launch
        }

        val request = SaveEntryRequest(
            measurementGroup = measurementGroupMapper.toDomain(currentViewState.measurementGroup),
            entry = currentViewState.existingEntry?.let { entryMapper.toDomain(it) },
            entryFields = currentViewState.entryFields.let { entryFieldMapper.toDomain(it.values.toList()) },
            dateTime = currentViewState.dateTime ?: LocalDateTime.now(),
        )

        saveEntryUseCase.execute(request, ::handleOnSave)
    }

    fun onDateChange(date: LocalDate) {
        val currentDateTime = currentViewState.dateTime ?: LocalDateTime.now()
        val dateTime = LocalDateTime.of(date, currentDateTime.toLocalTime())

        updateViewState(currentViewState.copy(dateTime = dateTime))
    }

    fun onTimeChange(time: LocalTime) {
        val currentDateTime = currentViewState.dateTime ?: LocalDateTime.now()
        val dateTime = LocalDateTime.of(currentDateTime.toLocalDate(), time)

        updateViewState(currentViewState.copy(dateTime = dateTime))
    }

    @Suppress("UnusedParameter")
    private fun handleOnSave(unit: Unit) {
        navigateTo(
            AddEditEntryDestination.EntrySaved(
                measurementGroupId = currentViewState.measurementGroup.id,
                source = currentViewState.navigationSource
            )
        )
    }

    private suspend fun getMeasurementGroup(): MeasurementGroupPresentationModel {
        val measurementGroupId = savedStateHandle.get<String>("measurementGroupId")
        require(measurementGroupId != null) { "MeasurementGroupId is null" }

        val measurementGroup = getMeasurementGroupRepository.getMeasurementGroup(measurementGroupId)
            ?.let { measurementGroupMapper.toPresentation(it) }

        require(measurementGroup != null) { "MeasurementGroup with ID#$measurementGroupId is null" }

        return measurementGroup
    }

    private fun getScheduleItem(
        measurementGroup: MeasurementGroupPresentationModel
    ): MeasurementGroupScheduleItemPresentationModel? {
        val scheduleItemId = savedStateHandle.get<String>("scheduleItemId") ?: return null

        return measurementGroup.scheduleItems.find { it.id == scheduleItemId }
    }

    private fun getExistingEntry(
        measurementGroup: MeasurementGroupPresentationModel
    ): MeasurementGroupEntryPresentationModel? {
        val existingEntryId = savedStateHandle.get<String>("entryId") ?: return null

        return measurementGroup.entries.find { it.id == existingEntryId }
    }

    private suspend fun getEntryFields(
        measurementGroup: MeasurementGroupPresentationModel,
        existingEntry: MeasurementGroupEntryPresentationModel?,
    ): Map<String, EntryFieldPresentationModel> {
        val request = GetEntryFieldsRequest(
            measurementGroup = measurementGroupMapper.toDomain(measurementGroup)
        )
        val results = getEntryFieldsUseCase.executeInBackground(request)
            .map { entryFieldMapper.toPresentation(it) }

        val fields = results.associateBy { it.fieldId }.toMutableMap()

        existingEntry?.values?.forEach { (key, value) ->
            fields[key]?.let {
                fields[key] = it.copy(value = value)
            }
        }

        return fields.toMap()
    }
}
