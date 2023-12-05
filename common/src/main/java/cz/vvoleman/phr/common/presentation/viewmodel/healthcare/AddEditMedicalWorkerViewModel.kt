package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMedicalWorkerViewModel @Inject constructor(
    private val getFacilitiesPagingStreamRepository: GetFacilitiesPagingStreamRepository,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditMedicalWorkerViewState, AddEditMedicalWorkerNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditMedicalWorkerViewModel"

    override fun onInit() {
        super.onInit()

        viewModelScope.launch {
            loadSelectedPatient()
        }
    }

    override fun initState(): AddEditMedicalWorkerViewState {
        return AddEditMedicalWorkerViewState()
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    fun onAddFacility() {
        updateViewState(
            currentViewState.copy(
                details = currentViewState.details + AddEditMedicalServiceItemUiModel(
                    id = "${System.currentTimeMillis()}",
                )
            )
        )

        Log.d(TAG, "onAddFacility: ${currentViewState.details}")
    }

    fun onFacilitySearch(query: String): Flow<PagingData<MedicalFacilityPresentationModel>> {
        if (query != currentViewState.query || currentViewState.facilityStream == null) {
            updateViewState(currentViewState.copy(query = query))
            val flow = getFacilitiesPagingStreamRepository
                .getFacilitiesPagingStream(query)
                .map { pagingData ->
                    pagingData.map {
                        facilityMapper.toPresentation(it)
                    }
                }
                .cachedIn(viewModelScope)

            updateViewState(
                currentViewState.copy(
                    facilityStream = flow
                )
            )

            return flow
        }

        return currentViewState.facilityStream ?: throw IllegalStateException("Facility stream is null")
    }
}
