package cz.vvoleman.phr.featureMedicalRecord.presentation.list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.event.PatientDeletedEvent
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeleteMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeletePatientUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedMedicalWorkersUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedProblemCategoriesUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class ListMedicalRecordsViewModel @Inject constructor(
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val getUsedProblemCategoriesUseCase: GetUsedProblemCategoriesUseCase,
    private val getUsedMedicalWorkersUseCase: GetUsedMedicalWorkersUseCase,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val deleteMedicalRecordUseCase: DeleteMedicalRecordUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordsViewState, ListMedicalRecordsNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {
    override val TAG = "ListMedicalRecordsViewModel"

    override suspend fun onInit() {
        super.onInit()

        listenForPatientChange()
        filterRecords()
    }

    override suspend fun initState(): ListMedicalRecordsViewState {
        return ListMedicalRecordsViewState()
    }

    fun onFilter() {
        filterRecords()
    }

    fun onRecordDelete(id: String) = viewModelScope.launch {
        deleteMedicalRecordUseCase.executeInBackground(id)
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

    fun onEventPatientDeleted(event: PatientDeletedEvent) = viewModelScope.launch {
        Log.d(TAG, "Patient deleted: ${event.patient.id}")
        deletePatientUseCase.execute(event.patient) {}
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
                getUsedMedicalWorkers(it)
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

    private fun getUsedMedicalWorkers(patient: PatientDomainModel) {
        Log.d(TAG, "Getting used medical workers for patient ${patient.id}")
        execute(getUsedMedicalWorkersUseCase, patient.id, ::handleMedicalWorkersResult)
    }

    private fun handleProblemCategoriesResult(problemCategories: List<ProblemCategoryDomainModel>) {
        updateViewState(currentViewState.copy(allProblemCategories = problemCategories))
    }

    private fun handleMedicalWorkersResult(medicalWorkers: List<MedicalWorkerDomainModel>) {
        updateViewState(currentViewState.copy(allMedicalWorkers = medicalWorkers))
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>>) {
        updateViewState(currentViewState.copy(groupedRecords = groupedRecords, isLoading = false))
    }
}
