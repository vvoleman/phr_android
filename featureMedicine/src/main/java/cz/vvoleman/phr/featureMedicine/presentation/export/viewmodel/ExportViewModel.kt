package cz.vvoleman.phr.featureMedicine.presentation.export.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportType
import cz.vvoleman.phr.featureMedicine.domain.model.export.GetDataForExportRequest
import cz.vvoleman.phr.featureMedicine.domain.usecase.export.GetDataForExportUseCase
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportNotification
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getDataForExportUseCase: GetDataForExportUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ExportViewState, ExportNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ExportViewModel"

    override fun initState(): ExportViewState {
        return ExportViewState()
    }

    override fun onInit() {
        super.onInit()

        viewModelScope.launch {
            loadSelectedPatient()

            getDataForExportUseCase.execute(
                GetDataForExportRequest(
                    patientId = currentViewState.patient!!.id,
                    LocalDateTime.now(),
                    medicine = emptyList(),
                    dateRange = Pair(null, null)
                )
            ) {
                Log.d(TAG, "Data for export: $it")
            }
        }
    }

    fun onExport() {
        notify(ExportNotification.CannotExport)
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }


}