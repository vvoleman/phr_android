package cz.vvoleman.phr.feature_medicalrecord.ui.view.binder

import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.GroupedItemsAdapter
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel

class MedicalRecordsBinder(
    private val groupedItemsDomainModelToUiMapper: GroupedItemsDomainModelToUiMapper
): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> {

    private lateinit var adapter: GroupedItemsAdapter<MedicalRecordUiModel>

    override fun bind(
        viewBinding: FragmentListMedicalRecordsBinding,
        viewState: ListMedicalRecordsViewState
    ) {
        val groups = viewState.groupedRecords.map { groupedItemsDomainModelToUiMapper.toUi(it) }

        adapter.submitList(groups)
    }

    fun setAdapter(adapter: GroupedItemsAdapter<MedicalRecordUiModel>) {
        this.adapter = adapter
    }

}