package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditEntryViewModel @Inject constructor(
    private val getMeasurementGroupRepository: GetMeasurementGroupRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditEntryViewState, AddEditEntryNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditEntryViewModel"

    override suspend fun initState(): AddEditEntryViewState {
        val measurementGroup = getMeasurementGroup()
        val entries = getEntries(measurementGroup.id)

        return AddEditEntryViewState(
            measurementGroup = measurementGroup,
            entries = entries
        )
    }

    private suspend fun getMeasurementGroup(): MeasurementGroupPresentationModel {
        val measurementGroupId = savedStateHandle.get<String>("measurementGroupId")
        require(measurementGroupId != null) { "MeasurementGroupId is null" }

        val measurementGroup = getMeasurementGroupRepository.getMeasurementGroup(measurementGroupId)
            ?.let { measurementGroupMapper.toPresentation(it) }

        require(measurementGroup != null) { "MeasurementGroup with ID#${measurementGroupId} is null" }

        return measurementGroup
    }

    private suspend fun getEntries(measurementGroupId: String): Map<String, MeasurementGroupEntryPresentationModel> {
        return emptyMap()
    }
}
