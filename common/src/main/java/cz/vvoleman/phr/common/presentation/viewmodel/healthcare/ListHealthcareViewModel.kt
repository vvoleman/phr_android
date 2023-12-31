package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.usecase.healthcare.DeleteMedicalWorkerUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerAdditionalInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareDestination
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHealthcareViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getMedicalWorkersUseCase: GetMedicalWorkersUseCase,
    private val deleteMedicalWorkerUseCase: DeleteMedicalWorkerUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val additionalMapper: MedicalWorkerAdditionalInfoPresentationModelToDomainMapper,
    private val workerMapper: MedicalWorkerPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListHealthcareViewState, ListHealthcareNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListHealthcareViewModel"

    override suspend fun initState(): ListHealthcareViewState {
        return ListHealthcareViewState()
    }

    override suspend fun onInit() {
        super.onInit()

        Log.d(TAG, "launching onInit")

        viewModelScope.launch {
            loadSelectedPatient()

            val request = GetMedicalWorkersRequest(currentViewState.patient!!.id)
            getMedicalWorkersUseCase.execute(request) {
                Log.d(TAG, "got medical workers: ${additionalMapper.toPresentation(it)}")
                updateViewState(currentViewState.copy(
                    medicalWorkers = additionalMapper.toPresentation(it)
                ))
            }
        }
    }

    fun onAddWorker() {
        navigateTo(ListHealthcareDestination.AddMedicalWorker)
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    fun onDeleteWorker(item: MedicalWorkerPresentationModel) = viewModelScope.launch {
        deleteMedicalWorkerUseCase.execute(workerMapper.toDomain(item)) {
            updateViewState(currentViewState.copy(
                medicalWorkers = currentViewState.medicalWorkers?.filter { it.key.id != item.id }
            ))
        }
    }

    fun onEditWorker(item: MedicalWorkerPresentationModel) {
        navigateTo(ListHealthcareDestination.EditMedicalWorker(item.id!!))
    }
}
