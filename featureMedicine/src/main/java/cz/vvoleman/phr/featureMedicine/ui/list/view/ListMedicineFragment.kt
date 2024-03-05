package cz.vvoleman.phr.featureMedicine.ui.list.view

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.data.repository.HealthcareRepository
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextSchedule
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModel
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemWithDetailsPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel.ListMedicineViewModel
import cz.vvoleman.phr.featureMedicine.ui.component.medicineDetailSheet.MedicineDetailSheet
import cz.vvoleman.phr.featureMedicine.ui.component.scheduleDetailDialog.ScheduleDetailAdapter
import cz.vvoleman.phr.featureMedicine.ui.component.scheduleDetailDialog.ScheduleDetailDialogFragment
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineCatalogueAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineFragmentAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.MedicineCatalogueViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel.TimelineViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemWithDetailsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicineFragment :
    BaseFragment<ListMedicineViewState, ListMedicineNotification, FragmentListMedicineBinding>(),
    NextSchedule.NextScheduleListener,
    TimelineFragment.TimelineInterface,
    MedicineCatalogueAdapter.MedicineCatalogueAdapterInterface,
    ScheduleDetailAdapter.ScheduleDetailListener {

    override val viewModel: ListMedicineViewModel by viewModels()
    private val timelineViewModel: TimelineViewModel by viewModels()
    private val medicineCatalogueViewModel: MedicineCatalogueViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding>

    private lateinit var fragmentAdapter: MedicineFragmentAdapter

    @Inject
    public lateinit var healthcareRepository: HealthcareRepository

    @Inject
    lateinit var nextScheduleMapper: NextScheduleUiModelToPresentationMapper

    @Inject
    lateinit var scheduleItemMapper: ScheduleItemWithDetailsUiModelToPresentationMapper

    @Inject
    lateinit var medicineScheduleMapper: MedicineScheduleUiModelToPresentationMapper

    private var counter = 0

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMedicineBinding {
        return FragmentListMedicineBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        timelineViewModel.setListener(this)
        medicineCatalogueViewModel.setListener(this)
        fragmentAdapter = MedicineFragmentAdapter(timelineViewModel, medicineCatalogueViewModel, this)
        (viewStateBinder as ListMedicineBinder).setFragmentAdapter(fragmentAdapter)

        binding.fabAddMedicalRecord.setOnClickListener {
            viewModel.onCreate()
        }
        binding.nextSchedule.setListener(this)
    }

    override fun handleNotification(notification: ListMedicineNotification) {
        when (notification) {
            ListMedicineNotification.DataLoaded -> showSnackbar(R.string.notification_data_loaded)
            ListMedicineNotification.UnableToLoad -> showSnackbar(R.string.notification_unable_to_load)
            ListMedicineNotification.AlarmNotDeleted -> showSnackbar(R.string.notification_alarm_not_deleted)
            ListMedicineNotification.ScheduleNotDeleted -> showSnackbar(R.string.notification_schedule_not_deleted)
            ListMedicineNotification.Deleted -> showSnackbar(R.string.notification_deleted)
            ListMedicineNotification.UnableToToggleAlarm -> showSnackbar(R.string.notification_unable_to_toggle_alarm)
            is ListMedicineNotification.OpenSchedule -> openScheduleDetailDialog(
                notification.dateTime,
                notification.items
            )
        }
    }

    override fun setOptionsMenu(): Int {
        return R.menu.options_list_menu
    }

    override fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_export -> {
                viewModel.onExportSelected()
                true
            }

            else -> super.onOptionsMenuItemSelected(menuItem)
        }
    }

    override fun onTimeOut() {
        viewModel.onNextScheduleTimeOut()
        Snackbar.make(binding.root, "Time out", Snackbar.LENGTH_SHORT).show()
    }

    override fun onNextScheduleClick(item: NextScheduleUiModel) {
        viewModel.onNextScheduleClick(nextScheduleMapper.toPresentation(item))
    }

    private fun openScheduleDetailDialog(
        dateTime: LocalDateTime,
        items: List<ScheduleItemWithDetailsPresentationModel>
    ) {
        val dialog = ScheduleDetailDialogFragment.newInstance(dateTime, scheduleItemMapper.toUi(items))
        dialog.setTargetFragment(this, SCHEDULE_DIALOG)
        dialog.show(requireFragmentManager(), ScheduleDetailDialogFragment.TAG)
    }

    override fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel) {
        Log.d("Timeline", "onTimelineItemClick: $item")
    }

    override fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean) {
        viewModel.onAlarmToggle(scheduleItemMapper.toPresentation(item), oldState)
    }

    override fun onOptionsMenuClick(item: MedicineScheduleUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_catalogue_item)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_stop_scheduling -> {
                    handleStopSchedulingItem(item)
                    true
                }
                R.id.action_edit -> {
                    viewModel.onEdit(item.id)
                    true
                }

                R.id.action_delete -> {
                    handleDeleteItemClick(item)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    override fun onCatalogueItemClick(item: MedicineScheduleUiModel) {
        val modal = MedicineDetailSheet.newInstance(item.medicine)
        modal.setTargetFragment(this, MEDICINE_DETAIL_SHEET)
        modal.show(requireFragmentManager(), "MedicineDetailSheet")
        Log.d("Catalogue", "onCatalogueItemClick: $item")
    }

    private fun handleDeleteItemClick(item: MedicineScheduleUiModel) {
        showConfirmDialog(
            getString(R.string.confirm_delete_title),
            getString(R.string.confirm_delete_message),
            Pair(getString(R.string.confirm_delete_positive)) {
                viewModel.onDelete(item.id)
            },
            Pair(getString(R.string.confirm_delete_negative)) {
                it.cancel()
            }
        )
    }

    private fun handleStopSchedulingItem(item: MedicineScheduleUiModel) {
        showConfirmDialog(
            getString(R.string.dialog_stop_scheduling_title),
            getString(R.string.dialog_stop_scheduling_message, item.medicine.name),
            Pair(getString(R.string.dialog_stop_scheduling_action_immediately)) {
                val model = medicineScheduleMapper.toPresentation(item)
                viewModel.onStopScheduling(model)
            },
            Pair(getString(R.string.dialog_stop_scheduling_action_cancel)) {
                it.cancel()
            }
        )
    }

    companion object {
        private const val TAG = "ListMedicineFragment"
        const val SCHEDULE_DIALOG = 1
        const val MEDICINE_DETAIL_SHEET = 2
    }

    override fun onLeafletOpen(scheduleItem: ScheduleItemWithDetailsUiModel) {
        Log.d(TAG, "onLeafletOpen: $scheduleItem")
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}
