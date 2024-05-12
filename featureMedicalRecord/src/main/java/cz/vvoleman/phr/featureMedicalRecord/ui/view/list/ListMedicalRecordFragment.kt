package cz.vvoleman.phr.featureMedicalRecord.ui.view.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.adapter.grouped.OnAdapterItemListener
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.ui.view.BaseExportFragment
import cz.vvoleman.phr.common.utils.getNameOfMonth
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.ListMedicalRecordViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.MedicalRecordUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.export.MedicalRecordPage
import cz.vvoleman.phr.featureMedicalRecord.ui.view.list.adapter.MedicalRecordsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicalRecordFragment :
    BaseExportFragment<
        ListMedicalRecordViewState,
        ListMedicalRecordNotification,
        FragmentListMedicalRecordsBinding
        >(),
    OnAdapterItemListener<MedicalRecordUiModel>,
    GroupedItemsAdapter.GroupedItemsAdapterInterface<MedicalRecordUiModel> {

    override val viewModel: ListMedicalRecordViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
        ViewStateBinder<ListMedicalRecordViewState, FragmentListMedicalRecordsBinding>

    @Inject
    lateinit var groupedItemsDomainModelToUiMapper: GroupedItemsDomainModelToUiMapper

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMedicalRecordsBinding {
        return FragmentListMedicalRecordsBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val listAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        val medicalBinder = (viewStateBinder as ListMedicalRecordBinder)
        binding.apply {
            fabAddMedicalRecord.setOnClickListener {
                viewModel.onRecordAdd()
            }
            fabFilterMedicalRecords.setOnClickListener {
                medicalBinder.toggleFilterSheet()
            }
        }

        medicalBinder.setAdapter(listAdapter)
        Log.d(TAG, "setupListeners: $medicalBinder")
        collectLifecycleFlow(medicalBinder.notification) {
            when (it) {
                is ListMedicalRecordBinder.Notification.OptionCheckChanged -> {
                    viewModel.onFilterOptionsToggle(it.item)
                }
                is ListMedicalRecordBinder.Notification.GroupByChanged -> {
                    viewModel.onFilterGroupByChange(it.item)
                }

                ListMedicalRecordBinder.Notification.FilterButtonPressed -> {
                    viewModel.onFilter()
                }
            }
        }
    }

    override fun handleNotification(notification: ListMedicalRecordNotification) {
        when (notification) {
            is ListMedicalRecordNotification.NotImplemented -> {
                showSnackbar("Not implemented")
            }
            is ListMedicalRecordNotification.RecordDeleted -> {
                showSnackbar(
                    R.string.record_deleted,
                    actions = listOf(
                        Pair(getString(cz.vvoleman.phr.common_datasource.R.string.action_undo)) {
                            viewModel.onRecordDeleteUndo(notification.id)
                        }
                    )
                )
            }
            is ListMedicalRecordNotification.Export -> {
                val pages = notification.params.map { MedicalRecordPage(it, requireContext()) }
                launchExport(pages)
            }
            ListMedicalRecordNotification.ExportFailed -> {
                showSnackbar(R.string.export_medical_record_failed)
            }
        }
    }

    override fun onItemOptionsMenuClicked(item: MedicalRecordUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_medical_record)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel.onRecordEdit(item.id)
                    true
                }
                R.id.action_delete -> {
                    showDeleteDialog(item)
                    true
                }
                R.id.action_download -> {
                    viewModel.onRecordExport(item.id)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    override fun onItemClicked(item: MedicalRecordUiModel) {
        viewModel.onRecordDetail(item.id)
    }

    override fun bindGroupedItems(
        groupBinding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<MedicalRecordUiModel>
    ) {
        val medicalRecordsAdapter = MedicalRecordsAdapter(this)
        // if item.value is LocalDate, then get date format (january 2023) and set it to textViewTitle
        // if item.value is String, then set it to textViewTitle
        // if item.value is something else, then set it to "-"
        var title = "-"
        if (item.value is LocalDate) {
            val date = item.value as LocalDate
            title = "${date.getNameOfMonth()} ${date.year}"
        } else if (item.value is String) {
            title = item.value as String
        }

        groupBinding.apply {
            textViewTitle.text = title
            recyclerView.apply {
                adapter = medicalRecordsAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        medicalRecordsAdapter.submitList(item.items)
    }

    override fun onDestroyGroupedItems(
        groupBinding: ItemGroupedItemsBinding,
    ) {
        groupBinding.recyclerView.adapter = null
    }

    private fun showDeleteDialog(item: MedicalRecordUiModel) {
        showConfirmDialog(
            R.string.delete_record_title,
            R.string.delete_record_message,
            positiveAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_delete) {
                viewModel.onRecordDelete(item.id)
            },
            negativeAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_cancel) {
                it.dismiss()
            }
        )
    }
}
