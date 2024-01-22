package cz.vvoleman.phr.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementDestination
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class ListMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMeasurementViewState, ListMeasurementNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListMeasurementViewModel"

    override suspend fun initState(): ListMeasurementViewState {
        val patient = getSelectedPatient()

        return ListMeasurementViewState(
            patient = patient,
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    fun onAddMeasurementGroup() {
        navigateTo(ListMeasurementDestination.AddMeasurementGroup)
    }

}
