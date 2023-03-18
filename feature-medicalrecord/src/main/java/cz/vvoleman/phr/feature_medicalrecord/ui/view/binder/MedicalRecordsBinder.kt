package cz.vvoleman.phr.feature_medicalrecord.ui.view.binder

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.sidesheet.SideSheetDialog
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.filter.FilterAdapter
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.FilterPair
import cz.vvoleman.phr.feature_medicalrecord.R
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.SheetRecordsFilterBinding
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.GroupByDomainModelViewIdMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MedicalRecordsBinder(
    private val groupedItemsDomainModelToUiMapper: GroupedItemsDomainModelToUiMapper,
    private val groupByDomainModelViewIdMapper: GroupByDomainModelViewIdMapper
) : BaseViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding, MedicalRecordsBinder.Notification>(),
    FilterAdapter.FilterAdapterListener {

    private lateinit var adapter: GroupedItemsAdapter<MedicalRecordUiModel>
    private lateinit var filterSheet: SideSheetDialog
    private lateinit var sheetBinding: SheetRecordsFilterBinding
    private lateinit var problemCategoryAdapter: FilterAdapter
    private lateinit var medicalWorkerAdapter: FilterAdapter

    private var previousState: ListMedicalRecordsViewState? = null

    override fun init(
        viewBinding: FragmentListMedicalRecordsBinding,
        context: Context,
        lifecycleScope: CoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        sheetBinding = SheetRecordsFilterBinding
            .inflate(LayoutInflater.from(context), null, false)
        filterSheet = SideSheetDialog(context)
        filterSheet.setContentView(sheetBinding.root)
        val topAppBar = filterSheet.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar?.setNavigationOnClickListener {
            filterSheet.hide()
        }

        sheetBinding.apply {
            radioGroupSort.setOnCheckedChangeListener { _, checkedId ->
                try {
                    Log.d("MedicalRecordsBinder", "Group by changed to $checkedId")
                    val options = groupByDomainModelViewIdMapper.toDomainModel(checkedId)
                    notify(
                        Notification.GroupByChanged(
                            options
                        )
                    )
                } catch (e: IllegalArgumentException) {
                    Log.e("MedicalRecordsBinder", "Unknown radio button id: $checkedId")
                }
            }
        }

        //problemCategoryAdapter = FilterAdapter(this)
    }

    override fun bind(
        viewBinding: FragmentListMedicalRecordsBinding,
        viewState: ListMedicalRecordsViewState
    ) {
        if (previousState == null || previousState!!.groupedRecords != viewState.groupedRecords)  {
            val groups = viewState.groupedRecords.map { groupedItemsDomainModelToUiMapper.toUi(it) }
            adapter.submitList(groups)
        }

        if (previousState == null || previousState!!.groupBy != viewState.groupBy) {
            val selectedGroup = groupByDomainModelViewIdMapper.toViewId(viewState.groupBy)
            sheetBinding.radioGroupSort.check(selectedGroup)
        }


        previousState = viewState.copy()
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
        data class OptionCheckChanged(val item: FilterPair) : Notification()
        data class GroupByChanged(val item: GroupByDomainModel) : Notification()
    }

    override fun onOptionCheckChanged(item: FilterPair) {
        notify(Notification.OptionCheckChanged(item))
    }

}