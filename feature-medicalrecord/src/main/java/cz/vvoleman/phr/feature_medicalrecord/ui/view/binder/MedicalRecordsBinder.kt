package cz.vvoleman.phr.feature_medicalrecord.ui.view.binder

import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.adapter.OnAdapterItemListener
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.MedicalRecordDomainModelToUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel

class MedicalRecordsBinder(
    private val uiMap: MedicalRecordDomainModelToUiMapper
): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> {

    private lateinit var adapter: GroupedItemsAdapter<MedicalRecordUiModel>

    override fun bind(
        viewBinding: FragmentListMedicalRecordsBinding,
        viewState: ListMedicalRecordsViewState
    ) {
        val groups = mutableListOf<GroupedItemsUiModel<MedicalRecordUiModel>>()
        for (group in viewState.groupedRecords) {
            val items = group.items.map { uiMap.toUi(it) }
            groups.add(GroupedItemsUiModel(group.value, items))
        }

        adapter?.submitList(groups)
    }

    fun setAdapter(adapter: GroupedItemsAdapter<MedicalRecordUiModel>) {
        this.adapter = adapter
    }

}