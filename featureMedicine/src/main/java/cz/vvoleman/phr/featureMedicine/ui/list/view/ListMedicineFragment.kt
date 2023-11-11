package cz.vvoleman.phr.featureMedicine.ui.list.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel.ListMedicineViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineFragmentAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.MedicineCatalogueFragment
import cz.vvoleman.phr.featureMedicine.ui.list.fragment.TimelineFragment
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.medicineDetailSheet.MedicineDetailSheet
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel
import cz.vvoleman.phr.featureMedicine.ui.nextSchedule.NextSchedule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicineFragment :
    BaseFragment<ListMedicineViewState, ListMedicineNotification, FragmentListMedicineBinding>(),
    NextSchedule.NextScheduleListener, TimelineFragment.TimelineInterface,
    MedicineCatalogueFragment.MedicineScheduleInterface {

    override val viewModel: ListMedicineViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding>

    private lateinit var fragmentAdapter: MedicineFragmentAdapter

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMedicineBinding {
        return FragmentListMedicineBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        fragmentAdapter = MedicineFragmentAdapter(this, this, this)
        (viewStateBinder as ListMedicineBinder).setFragmentAdapter(fragmentAdapter)

        binding.fabAddMedicalRecord.setOnClickListener {
            viewModel.onCreate()
        }
        binding.nextSchedule.setListener(this)
    }

    override fun handleNotification(notification: ListMedicineNotification) {
        when (notification) {
            is ListMedicineNotification.DataLoaded -> showSnackbar(R.string.notification_data_loaded)
            is ListMedicineNotification.UnableToLoad -> showSnackbar(R.string.notification_unable_to_load)
            is ListMedicineNotification.AlarmNotDeleted -> showSnackbar(R.string.notification_alarm_not_deleted)
            is ListMedicineNotification.ScheduleNotDeleted -> showSnackbar(R.string.notification_schedule_not_deleted)
            is ListMedicineNotification.Deleted -> showSnackbar(R.string.notification_deleted)
        }
    }

    override fun onTimeOut() {
        viewModel.onNextScheduleTimeOut()
        Snackbar.make(binding.root, "Time out", Snackbar.LENGTH_SHORT).show()
    }

    override fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel) {
        Log.d("Timeline", "onTimelineItemClick: $item")
    }

    override fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean) {
        Log.d("Timeline", "onTimelineItemAlarmToggle: $item, $oldState")
    }

    override fun onCatalogueItemClick(item: MedicineScheduleUiModel) {
        val modal = MedicineDetailSheet(item.medicine)
        modal.show(childFragmentManager, "MedicineDetailSheet")
        Log.d("Catalogue", "onCatalogueItemClick: $item")
    }

    override fun onCatalogueItemEdit(item: MedicineScheduleUiModel) {
        viewModel.onEdit(item.id)
    }

    override fun onCatalogueItemDelete(item: MedicineScheduleUiModel) {
        showConfirmDialog(
            getString(R.string.confirm_delete_title),
            getString(R.string.confirm_delete_message),
            Pair(getString(R.string.confirm_delete_positive)) {
                viewModel.onDelete(item.id)
            },
            Pair(getString(R.string.confirm_delete_negative)) {
                it.cancel()
            })
    }

    companion object {
        private const val TAG = "ListMedicineFragment"
    }

}
