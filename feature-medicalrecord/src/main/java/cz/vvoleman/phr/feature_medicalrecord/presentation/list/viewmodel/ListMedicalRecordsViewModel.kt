package cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetUsedProblemCategoriesUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsDestination
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMedicalRecordsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val getUsedProblemCategoriesUseCase: GetUsedProblemCategoriesUseCase,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientDataStore: PatientDataStore,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordsViewState, ListMedicalRecordsNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {
    private var counter = 0
    override val TAG = "ListMedicalRecordsViewModel"

    override fun onInit() {
        super.onInit()

        listenForPatientChange()
        filterRecords()
    }

    override fun initState(): ListMedicalRecordsViewState {
        return ListMedicalRecordsViewState()
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

    fun onFilterGroupByChange(groupBy: GroupByDomainModel) {
        updateViewState(currentViewState.copy(groupBy = groupBy))
        filterRecords()
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

    fun onRecordSelect(id: String) = viewModelScope.launch {
//        val patient = getSelectedPatientUseCase.execute(null).first()
//        val newPatient = (patient.id.toInt() % 2) + 1
//        patientDataStore.updatePatient(newPatient)
    }

    fun onSelect() {
        navigateTo(ListMedicalRecordsDestination.NewMedicalRecord)
    }

    private fun listenForPatientChange() {
        val flow = getSelectedPatientUseCase.execute(null)
        viewModelScope.launch {
            flow.collect {
                Log.d(TAG, "Patient changed: ${it.id}")
                getUsedProblemCategories(it)
                filterRecords()
            }
        }
    }

    private fun filterRecords() {
        val filterRequest = listViewStateToDomainMapper.toDomain(currentViewState)
        updateViewState(currentViewState.copy(isLoading = true))
        execute(getFilteredRecordsUseCase, filterRequest, ::handleRecordsResult)
    }

    private fun getUsedProblemCategories(patient: PatientDomainModel) {
        Log.d(TAG, "Getting used problem categories for patient ${patient.id}")
        execute(getUsedProblemCategoriesUseCase, patient.id, ::handleProblemCategoriesResult)
    }

    private fun handleProblemCategoriesResult(problemCategories: List<ProblemCategoryDomainModel>) {
        Log.d(TAG, "Got problem categories: $problemCategories")
        updateViewState(currentViewState.copy(selectedProblemCategories = problemCategories.map { it.id }))
        Log.d(TAG, "Updated view state: ${problemCategories}")
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>>) {
        updateViewState(currentViewState.copy(groupedRecords = groupedRecords, isLoading = false))
    }

    companion object {
        private const val STATE = "ListMedicalRecordsViewState"
    }

}