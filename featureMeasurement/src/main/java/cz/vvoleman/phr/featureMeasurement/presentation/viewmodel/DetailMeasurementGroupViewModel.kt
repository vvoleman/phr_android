package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMeasurementGroupViewModel @Inject constructor(
    private val getMeasurementGroupRepository: GetMeasurementGroupRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<DetailMeasurementGroupViewState, DetailMeasurementGroupNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "DetailMeasurementGroupViewModel"

    override suspend fun initState(): DetailMeasurementGroupViewState {
        val measurementGroup = getMeasurementGroup()

        return DetailMeasurementGroupViewState(
            measurementGroup = measurementGroup
        )
    }

    private suspend fun getMeasurementGroup(): MeasurementGroupPresentationModel {
        val id = savedStateHandle.get<String>("measurementGroupId")
        requireNotNull(id) { "Measurement group id is null" }

        val model = getMeasurementGroupRepository.getMeasurementGroup(id)
        requireNotNull(model) { "No measurement group found for id $id" }

        return measurementGroupMapper.toPresentation(model)
    }
}
