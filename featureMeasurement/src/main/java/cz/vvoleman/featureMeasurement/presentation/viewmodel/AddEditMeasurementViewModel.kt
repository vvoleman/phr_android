package cz.vvoleman.featureMeasurement.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AddEditMeasurementViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
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

        return AddEditMeasurementViewState(
            patient = patient
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()

        return patientMapper.toPresentation(patient)
    }
}
