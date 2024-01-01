package cz.vvoleman.phr.featureMedicalRecord.ui.view.binder

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.sidesheet.SideSheetDialog
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.filter.FilterAdapter
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.FilterPair
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.SheetRecordsFilterBinding
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.GroupByDomainModelViewIdMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.MedicalRecordUiModel

@Suppress("MaximumLineLength", "MaxLineLength")
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
        lifecycleScope: LifecycleCoroutineScope
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

        problemCategoryAdapter = FilterAdapter(this)
        medicalWorkerAdapter = FilterAdapter(this)

        sheetBinding.recyclerViewProblemCategories.apply {
            adapter = problemCategoryAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        sheetBinding.recyclerViewMedicalWorkers.apply {
            adapter = medicalWorkerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    override fun bind(
        viewBinding: FragmentListMedicalRecordsBinding,
        viewState: ListMedicalRecordsViewState
    ) {
        if (previousState == null || previousState!!.groupedRecords != viewState.groupedRecords) {
            val groups = viewState.groupedRecords.map { groupedItemsDomainModelToUiMapper.toUi(it) }
            adapter.submitList(groups)
        }

        if (previousState == null || previousState!!.groupBy != viewState.groupBy) {
            val selectedGroup = groupByDomainModelViewIdMapper.toViewId(viewState.groupBy)
            sheetBinding.radioGroupSort.check(selectedGroup)
        }

        if (previousState == null || previousState!!.allProblemCategories != viewState.allProblemCategories) {
            problemCategoryAdapter.submitList(
                viewState.allProblemCategories.map {
                    FilterPair(
                        it.id,
                        it.name,
                        it,
                        viewState.selectedProblemCategories.contains(it.id)
                    )
                }
            )
        }

        if (previousState == null || previousState!!.allMedicalWorkers != viewState.allMedicalWorkers) {
            medicalWorkerAdapter.submitList(
                viewState.allMedicalWorkers.map {
                    FilterPair(
                        it.id!!,
                        it.name,
                        it,
                        viewState.selectedMedicalWorkers.contains(it.id)
                    )
                }
            )
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

    override fun onDestroy(viewBinding: FragmentListMedicalRecordsBinding) {
        super.onDestroy(viewBinding)

        viewBinding.recyclerView.adapter = null
    }
}
