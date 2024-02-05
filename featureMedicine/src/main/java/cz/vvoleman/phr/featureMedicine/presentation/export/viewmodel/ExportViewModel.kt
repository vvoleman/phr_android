package cz.vvoleman.phr.featureMedicine.presentation.export.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.export.ExportType
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.export.PermissionStatus
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.featureMedicine.domain.model.export.GetDataForExportRequest
import cz.vvoleman.phr.featureMedicine.domain.usecase.export.GetDataForExportUseCase
import cz.vvoleman.phr.featureMedicine.presentation.export.mapper.ExportMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportNotification
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportParamsPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getDataForExportUseCase: GetDataForExportUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val exportMapper: ExportMedicineSchedulePresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ExportViewState, ExportNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ExportViewModel"

    override suspend fun initState(): ExportViewState {
        val todayMidnight = LocalDate.now().atTime(LocalTime.MAX)
        val weekAgo = LocalDate.now().minusDays(TimeConstants.DAYS_IN_WEEK.toLong()).atTime(LocalTime.MIN)

        return ExportViewState(
            dateRange = Pair(weekAgo, todayMidnight),
        )
    }

    override suspend fun onInit() {
        super.onInit()

        viewModelScope.launch {
            loadSelectedPatient()
        }
    }

    fun onExport() {
        loadExportData {
            notify(
                ExportNotification.ExportAs(
                    type = currentViewState.exportType,
                    params = ExportParamsPresentationModel(
                        data = it,
                        patient = currentViewState.patient!!,
                        startAt = currentViewState.dateRange.first,
                        endAt = currentViewState.dateRange.second,
                    )
                )
            )
        }
    }

    fun onPreview() {
        loadExportData()
    }

    fun onExportTypeChanged(exportType: ExportType) {
        updateViewState(currentViewState.copy(exportType = exportType))
    }

    fun onStartAtChanged(startAt: LocalDateTime) {
        updateViewState(currentViewState.copy(dateRange = Pair(startAt, currentViewState.dateRange.second)))
    }

    fun onEndAtChanged(endAt: LocalDateTime) {
        updateViewState(currentViewState.copy(dateRange = Pair(currentViewState.dateRange.first, endAt)))
    }

    fun onPermissionDenied(status: PermissionStatus) {
        updateViewState(currentViewState.copy(permissionStatus = status))
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
    }

    private fun loadExportData(onResult: ((List<ExportMedicineSchedulePresentationModel>) -> Unit)? = null) {
        val request = GetDataForExportRequest(
            patientId = currentViewState.patient!!.id,
            LocalDateTime.now(),
            medicine = emptyList(),
            dateRange = currentViewState.dateRange,
        )
        execute(getDataForExportUseCase, request, { list ->
            updateViewState(currentViewState.copy(exportData = list.map { exportMapper.toPresentation(it) }))
            onResult?.let { it(currentViewState.exportData) }
        }, {
            notify(ExportNotification.CannotLoadData)
        })
    }
}
