package cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel

import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupedMedicalRecordsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import javax.inject.Inject

class ListMedicalRecordsViewModel @Inject constructor(
    private val getFilteredRecordsUseCase: GetFilteredRecordsUseCase,
    private val listViewStateToDomainMapper: ListViewStateToDomainMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListMedicalRecordsViewState, ListMedicalRecordsNotification>(useCaseExecutorProvider) {

    override fun initState(): ListMedicalRecordsViewState {
        return ListMedicalRecordsViewState()
    }

    init {
        filterRecords()
    }

    fun onFilter() {
        filterRecords()
    }

    private fun filterRecords() {
        val filterRequest = listViewStateToDomainMapper.toDomain(currentViewState)
        updateViewState(currentViewState.copy(isLoading = true))
        execute(getFilteredRecordsUseCase, filterRequest, ::handleRecordsResult)
    }

    private fun handleRecordsResult(groupedRecords: List<GroupedMedicalRecordsDomainModel>) {
        updateViewState(currentViewState.copy(groupedRecords=groupedRecords, isLoading = false))
    }
}