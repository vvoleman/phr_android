package cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsDestination
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMedicalRecordsViewModel @Inject constructor(
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientDataStore: PatientDataStore,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordsViewState, ListMedicalRecordsNotification>(useCaseExecutorProvider) {

    override val TAG = "ListMedicalRecordsViewModel"

    override fun initState(): ListMedicalRecordsViewState {
        return ListMedicalRecordsViewState()
    }

    init {
        listenForPatientChange()
        filterRecords()
    }

    fun onFilter() {
        filterRecords()
    }

    fun onRecordDelete(id: String) {
        notify(ListMedicalRecordsNotification.RecordDeleted(id))
        filterRecords()
    }

    fun onRecordAdd() {
        navigateTo(ListMedicalRecordsDestination.NewMedicalRecord)
    }

    fun onRecordExport(id: String) {
        notify(ListMedicalRecordsNotification.NotImplemented)
    }

    fun onRecordEdit(id: String) {
        navigateTo(ListMedicalRecordsDestination.EditMedicalRecord(id))
    }

    fun onRecordDeleteUndo(id: String) {
        notify(ListMedicalRecordsNotification.NotImplemented)
    }

    fun onRecordSelect(id: String) = viewModelScope.launch{
        Log.d(TAG, "onRecordSelect")
        val patient = getSelectedPatientUseCase.execute(null).first()
        patientDataStore.updatePatient((patient.id.toInt() % 2) + 1)
    }

    fun onSelect() {
        navigateTo(ListMedicalRecordsDestination.NewMedicalRecord)
    }

    private fun listenForPatientChange() {
        val flow = getSelectedPatientUseCase.execute(null)
        Log.d(TAG, "listenForPatientChange")
        viewModelScope.launch {
            flow.collect {
                filterRecords()
            }
        }
    }

    private fun filterRecords() {
        Log.d(TAG, "filterRecords")
        val filterRequest = listViewStateToDomainMapper.toDomain(currentViewState)
        updateViewState(currentViewState.copy(isLoading = true))
        execute(getFilteredRecordsUseCase, filterRequest, ::handleRecordsResult)
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>>) {
        Log.d(TAG, "handleRecordsResult")
        updateViewState(currentViewState.copy(groupedRecords=groupedRecords, isLoading = false))
    }
}