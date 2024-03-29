package cz.vvoleman.phr.featureMedicalRecord.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.domain.event.PatientDeletedEvent
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.adapter.grouped.OnAdapterItemListener
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.getNameOfMonth
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.viewmodel.ListMedicalRecordsViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.GroupedItemsDomainModelToUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.MedicalRecordUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.binder.MedicalRecordsBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.list.adapter.MedicalRecordsAdapter
import cz.vvoleman.phr.featureMedicalRecord.ui.view.list.adapter.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicalRecordsFragment :
    BaseFragment<
        ListMedicalRecordsViewState,
        ListMedicalRecordsNotification,
        FragmentListMedicalRecordsBinding
        >(),
    OnAdapterItemListener<MedicalRecordUiModel> {

    override val viewModel: ListMedicalRecordsViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
        ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding>

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
        val medicalBinder = (viewStateBinder as MedicalRecordsBinder)
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
                is MedicalRecordsBinder.Notification.OptionCheckChanged -> {
                    Log.d(TAG, "OptionCheckChanged: $it")
                }
                is MedicalRecordsBinder.Notification.GroupByChanged -> {
                    viewModel.onFilterGroupByChange(it.item)
                }
            }
        }
        collectLatestLifecycleFlow(viewModel.viewState) {
            if (it == null) return@collectLatestLifecycleFlow

            listAdapter.submitList(it.groupedRecords.map { groupedItemsDomainModelToUiMapper.toUi(it) })
        }
    }

    override fun handleNotification(notification: ListMedicalRecordsNotification) {
        when (notification) {
            is ListMedicalRecordsNotification.Success -> {
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
            }
            is ListMedicalRecordsNotification.NotFoundError -> {
                Snackbar.make(binding.root, "Not found", Snackbar.LENGTH_SHORT).show()
            }
            is ListMedicalRecordsNotification.NotImplemented -> {
                Snackbar.make(binding.root, "Not implemented", Snackbar.LENGTH_SHORT).show()
            }
            is ListMedicalRecordsNotification.RecordDeleted -> {
                Snackbar.make(binding.root, "Record deleted", Snackbar.LENGTH_SHORT).setAction("Undo") {
                    viewModel.onRecordDeleteUndo(notification.id)
                }.show()
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
                    Log.d(TAG, "onItemOptionsMenuClicked: $item")
                    viewModel.onRecordDelete(item.id)
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
        viewModel.onRecordSelect(item.id)
    }

    override fun onItemDelete(item: MedicalRecordUiModel) {
        viewModel.onRecordDelete(item.id)
    }

    override fun bind(
        binding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<MedicalRecordUiModel>
    ) {
        val medicalRecordsAdapter = MedicalRecordsAdapter(this)
        val swipeToDeleteCallback = SwipeToDeleteCallback(medicalRecordsAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
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

        binding.apply {
            textViewTitle.text = title
            recyclerView.apply {
                adapter = medicalRecordsAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        medicalRecordsAdapter.submitList(item.items)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onPatientDeleted(event: PatientDeletedEvent) {
        viewModel.onEventPatientDeleted(event)
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }
}
