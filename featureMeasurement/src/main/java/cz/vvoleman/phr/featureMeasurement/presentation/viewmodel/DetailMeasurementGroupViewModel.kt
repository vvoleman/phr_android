package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMeasurement.domain.model.detail.GetFieldStatsRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntryRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.detail.GetFieldStatsUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.detail.FieldStatsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.FieldStatsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMeasurementGroupViewModel @Inject constructor(
    private val getFieldStatsUseCase: GetFieldStatsUseCase,
    private val getMeasurementGroupRepository: GetMeasurementGroupRepository,
    private val deleteEntryRepository: DeleteEntryRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    private val fieldStatsMapper: FieldStatsPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<DetailMeasurementGroupViewState, DetailMeasurementGroupNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "DetailMeasurementGroupViewModel"

    override suspend fun initState(): DetailMeasurementGroupViewState {
        val measurementGroup = getMeasurementGroup()
        val fieldStats = getFieldStats(measurementGroup)

        return DetailMeasurementGroupViewState(
            measurementGroup = measurementGroup,
            fieldStats = fieldStats,
        )
    }

    private suspend fun getMeasurementGroup(existingId: String? = null): MeasurementGroupPresentationModel {
        val id = existingId ?: savedStateHandle.get<String>("measurementGroupId")

        requireNotNull(id) { "Measurement group id is null" }

        val model = getMeasurementGroupRepository.getMeasurementGroup(id)
        requireNotNull(model) { "No measurement group found for id $id" }

        return measurementGroupMapper.toPresentation(model)
    }

    private suspend fun getFieldStats(
        measurementGroup: MeasurementGroupPresentationModel
    ): List<FieldStatsPresentationModel> {
        val request = GetFieldStatsRequest(
            measurementGroup = measurementGroupMapper.toDomain(measurementGroup),
        )

        val result = getFieldStatsUseCase.executeInBackground(request)

        return fieldStatsMapper.toPresentation(result)
    }

    fun onAddEntry() {
        navigateTo(
            DetailMeasurementGroupDestination.AddEntry(
                measurementGroupId = currentViewState.measurementGroup.id,
                name = currentViewState.measurementGroup.name
            )
        )
    }

    fun onDeleteEntry(model: MeasurementGroupEntryPresentationModel) = viewModelScope.launch {
        deleteEntryRepository.deleteEntry(model.id)

        val measurementGroup = getMeasurementGroup(currentViewState.measurementGroup.id)

        updateViewState(
            currentViewState.copy(
                measurementGroup = measurementGroup
            )
        )
    }

    fun onEditEntry(model: MeasurementGroupEntryPresentationModel) {
        navigateTo(
            DetailMeasurementGroupDestination.EditEntry(
                measurementGroupId = currentViewState.measurementGroup.id,
                entryId = model.id,
                name = currentViewState.measurementGroup.name,
            )
        )
    }

    fun onExport() {
        notify(DetailMeasurementGroupNotification.Export(currentViewState.measurementGroup))
    }
}
