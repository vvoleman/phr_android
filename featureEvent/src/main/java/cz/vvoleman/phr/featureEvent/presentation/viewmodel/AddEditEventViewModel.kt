package cz.vvoleman.phr.featureEvent.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditEventViewState, AddEditEventNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "AddEditEventViewModel"

    override suspend fun initState(): AddEditEventViewState {
        val patient = getSelectedPatient()

        return AddEditEventViewState(
            patient = patient,
            event = null
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }
}
