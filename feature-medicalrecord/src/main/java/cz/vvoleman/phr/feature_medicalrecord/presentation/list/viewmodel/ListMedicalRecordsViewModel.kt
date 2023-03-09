package cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel

import android.util.Log
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupedMedicalRecordsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMedicalRecordsViewModel @Inject constructor(
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordsViewState, ListMedicalRecordsNotification>(useCaseExecutorProvider) {

    override val TAG = "ListMedicalRecordsViewModel"

    override fun initState(): ListMedicalRecordsViewState {
        return ListMedicalRecordsViewState()
    }

    init {
        Log.d(TAG, "init")
        filterRecords()
    }

    fun onFilter() {
        filterRecords()
    }

    fun onSelect() {
        notify(ListMedicalRecordsNotification.Success)
    }

    private fun filterRecords() {
        Log.d(TAG, "filterRecords")
        val filterRequest = listViewStateToDomainMapper.toDomain(currentViewState)
        updateViewState(currentViewState.copy(isLoading = true))
        execute(getFilteredRecordsUseCase, filterRequest, ::handleRecordsResult)
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedMedicalRecordsDomainModel>) {
        Log.d(TAG, "handleRecordsResult")
        updateViewState(currentViewState.copy(groupedRecords=groupedRecords, isLoading = false))
    }
}