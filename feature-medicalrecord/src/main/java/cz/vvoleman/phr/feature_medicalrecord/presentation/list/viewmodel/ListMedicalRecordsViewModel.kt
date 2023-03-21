package cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetUsedProblemCategoriesUseCase
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
                getUsedProblemCategories(it)
            }
        }
    }

    private fun filterRecords() {
        Log.d(TAG, "filterRecords ${counter++}")
        val filterRequest = listViewStateToDomainMapper.toDomain(currentViewState)
        updateViewState(currentViewState.copy(isLoading = true))
        execute(getFilteredRecordsUseCase, filterRequest, ::handleRecordsResult)
    }

    private fun getUsedProblemCategories(patient: PatientDomainModel) {
        execute(getUsedProblemCategoriesUseCase, patient.id, ::handleProblemCategoriesResult)
    }

    private fun handleProblemCategoriesResult(problemCategories: List<ProblemCategoryDomainModel>) {
        Log.d(TAG, "handleProblemCategoriesResult ${problemCategories}")
        updateViewState(currentViewState.copy(selectedProblemCategories = problemCategories.map { it.id }))
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>>) {
        Log.d(TAG, "handleRecordsResult")
        updateViewState(currentViewState.copy(groupedRecords = groupedRecords, isLoading = false))
    }

    companion object {
        private const val STATE = "ListMedicalRecordsViewState"
    }

}