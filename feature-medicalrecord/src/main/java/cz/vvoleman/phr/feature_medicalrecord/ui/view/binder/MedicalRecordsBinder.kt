package cz.vvoleman.phr.feature_medicalrecord.ui.view.binder

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.sidesheet.SideSheetDialog
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.filter.FilterAdapter
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.FilterPair
import cz.vvoleman.phr.feature_medicalrecord.R
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.SheetRecordsFilterBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MedicalRecordsBinder(
    private val groupedItemsDomainModelToUiMapper: GroupedItemsDomainModelToUiMapper
): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding>, FilterAdapter.FilterAdapterListener {

    private lateinit var adapter: GroupedItemsAdapter<MedicalRecordUiModel>
    private lateinit var filterSheet: SideSheetDialog
    private lateinit var problemCategoryAdapter: FilterAdapter
    private lateinit var medicalWorkerAdapter: FilterAdapter

    private val _notification = MutableSharedFlow<Notification>()
    val notification = _notification.asSharedFlow()

    override fun init(viewBinding: FragmentListMedicalRecordsBinding, context: Context) {
        val sideSheetBinding = SheetRecordsFilterBinding
            .inflate(LayoutInflater.from(context), null, false)
        filterSheet = SideSheetDialog(context)
        filterSheet.setContentView(sideSheetBinding.root)
        val topAppBar = filterSheet.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar?.setNavigationOnClickListener {
            filterSheet.hide()
        }

        //problemCategoryAdapter = FilterAdapter(this)
    }

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

    fun toggleFilterSheet() {
        if (filterSheet.isShowing) {
            filterSheet.hide()
        } else {
            filterSheet.show()
        }
    }

    sealed class Notification {
        data class OptionCheckChanged(val item: FilterPair): Notification()
    }

    override fun onOptionCheckChanged(item: FilterPair) {
        _notification.tryEmit(Notification.OptionCheckChanged(item))
    }

}