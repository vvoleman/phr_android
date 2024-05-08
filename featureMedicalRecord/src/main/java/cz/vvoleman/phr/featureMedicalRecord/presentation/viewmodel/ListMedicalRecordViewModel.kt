package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.ui.model.FilterPair
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeleteMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeletePatientUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedMedicalWorkersUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedProblemCategoriesUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core.MedicalRecordPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.list.ListViewStateToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.export.ExportMedicalRecordParamsPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class ListMedicalRecordViewModel @Inject constructor(
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val getUsedProblemCategoriesUseCase: GetUsedProblemCategoriesUseCase,
    private val getUsedMedicalWorkersUseCase: GetUsedMedicalWorkersUseCase,
    private val medicalRecordMapper: MedicalRecordPresentationModelToDomainMapper,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val deleteMedicalRecordUseCase: DeleteMedicalRecordUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordViewState, ListMedicalRecordNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {
    override val TAG = "ListMedicalRecordsViewModel"

    override suspend fun onInit() {
        super.onInit()

        listenForPatientChange()
        filterRecords()
    }

    override suspend fun initState(): ListMedicalRecordViewState {
        return ListMedicalRecordViewState()
    }

    fun onFilter() {
        filterRecords()
    }

    fun onRecordDelete(id: String) = viewModelScope.launch {
        deleteMedicalRecordUseCase.executeInBackground(id)
        notify(ListMedicalRecordNotification.RecordDeleted(id))
        filterRecords()
    }

    fun onRecordAdd() {
        navigateTo(ListMedicalRecordDestination.NewMedicalRecord)
    }

    fun onFilterGroupByChange(groupBy: GroupByDomainModel) {
        updateViewState(currentViewState.copy(groupBy = groupBy))
    }

    fun onRecordExport(id: String) {
        // Find medical record
        val medicalRecord = currentViewState.groupedRecords
            ?.flatMap { it.items }
            ?.find { it.id == id }
            ?.let { medicalRecordMapper.toPresentation(it) }

        if (medicalRecord == null) {
            notify(ListMedicalRecordNotification.ExportFailed)
            return
        }

        // Split into params for each asset
        val params = medicalRecord.assets.map {
            ExportMedicalRecordParamsPresentationModel(
                medicalRecord = medicalRecord,
                asset = it
            )
        }

        notify(ListMedicalRecordNotification.Export(params))
    }

    fun onRecordEdit(id: String) {
        navigateTo(ListMedicalRecordDestination.EditMedicalRecord(id))
    }

    fun onRecordDeleteUndo(id: String) {
        notify(ListMedicalRecordNotification.NotImplemented)
    }

    fun onRecordDetail(id: String) {
        navigateTo(ListMedicalRecordDestination.DetailMedicalRecord(id))
    }

    fun onFilterOptionsToggle(option: FilterPair) {
        when (option.objectValue) {
            is ProblemCategoryDomainModel -> {
                val id = option.id
                val selected = currentViewState.selectedProblemCategories.toMutableList()
                if (selected.contains(id)) {
                    selected.remove(id)
                } else {
                    selected.add(id)
                }
                updateViewState(currentViewState.copy(selectedProblemCategories = selected))
            }
            is MedicalWorkerDomainModel -> {
                val id = option.id
                val selected = currentViewState.selectedMedicalWorkers.toMutableList()
                if (selected.contains(id)) {
                    selected.remove(id)
                } else {
                    selected.add(id)
                }
                updateViewState(currentViewState.copy(selectedMedicalWorkers = selected))
            }
            else -> {
                Log.d(TAG, "onFilterOptionsToggle other: ${option.objectValue}")
            }
        }
    }

    fun onSelect() {
        navigateTo(ListMedicalRecordDestination.NewMedicalRecord)
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
