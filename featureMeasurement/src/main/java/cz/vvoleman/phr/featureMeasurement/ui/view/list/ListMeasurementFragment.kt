package cz.vvoleman.phr.featureMeasurement.ui.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextSchedule
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModel
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.ListMeasurementViewModel
import cz.vvoleman.phr.featureMeasurement.ui.adapter.MeasurementTimelineAdapter
import cz.vvoleman.phr.featureMeasurement.ui.adapter.list.MeasurementFragmentAdapter
import cz.vvoleman.phr.featureMeasurement.ui.adapter.list.MeasurementGroupAdapter
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.ScheduledMeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementGroupViewModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementTimelineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMeasurementFragment :
    BaseFragment<ListMeasurementViewState, ListMeasurementNotification, FragmentListMeasurementBinding>(),
    NextSchedule.NextScheduleListener, MeasurementGroupAdapter.MeasurementGroupAdapterInterface,
    MeasurementTimelineAdapter.MeasurementTimelineAdapterInterface {

    override val viewModel: ListMeasurementViewModel by viewModels()
    private val measurementGroupViewModel: MeasurementGroupViewModel by viewModels()
    private val timelineViewModel: MeasurementTimelineViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListMeasurementDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding>

    @Inject
    lateinit var measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper

    @Inject
    lateinit var scheduleMapper: ScheduledMeasurementGroupUiModelToPresentationMapper

    private var fragmentAdapter: MeasurementFragmentAdapter? = null

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListMeasurementBinding {
        return FragmentListMeasurementBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        measurementGroupViewModel.setListener(this)
        timelineViewModel.setListener(this)
        fragmentAdapter = MeasurementFragmentAdapter(
            measurementGroupViewModel,
            timelineViewModel,
            this
        )
        (viewStateBinder as ListMeasurementBinder).setFragmentAdapter(fragmentAdapter!!)

        binding.nextSchedule.setListener(this)
        binding.fabAddMeasurementGroup.setOnClickListener {
            viewModel.onAddMeasurementGroup()
        }
    }

    override fun handleNotification(notification: ListMeasurementNotification) {
        when (notification) {
            ListMeasurementNotification.NoNextSchedule -> {
                showSnackbar(R.string.no_next_schedule_error, Snackbar.LENGTH_LONG)
            }

            is ListMeasurementNotification.OpenNextScheduleDetail -> {
                showSnackbar(notification.nextSchedule.dateTime.toString())
            }
        }
    }

    override fun onTimeOut() {
        viewModel.onNextScheduleTimeOut()
    }

    override fun onNextScheduleClick(item: NextScheduleUiModel) {
        viewModel.onNextSchedule()
    }

    override fun onDestroyView() {
        fragmentAdapter = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }

    override fun onMeasurementGroupClick(item: MeasurementGroupUiModel) {
        viewModel.onDetailMeasurementGroup(measurementGroupMapper.toPresentation(item))
    }

    override fun onMeasurementGroupOptionsClick(item: MeasurementGroupUiModel, anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.inflate(R.menu.options_measurement_group)

        val model = measurementGroupMapper.toPresentation(item)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_detail -> {
                    viewModel.onDetailMeasurementGroup(model)
                    true
                }
                R.id.action_edit -> {
                    viewModel.onEditMeasurementGroup(model.id)
                    true
                }
                R.id.action_delete -> {
                    handleDeleteMeasurementGroupDialog(model)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun handleDeleteMeasurementGroupDialog(model: MeasurementGroupPresentationModel) {
        showConfirmDialog(
            title = R.string.dialog_delete_measurement_group_title,
            message = R.string.dialog_delete_measurement_group_message,
            positiveAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_delete) {
                viewModel.onDeleteMeasurementGroup(model)
            },
            negativeAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_cancel) {
                //do nothing
            }
        )
    }

    override fun onMeasurementTimelineClick(item: ScheduledMeasurementGroupUiModel) {
        showSnackbar("Clicked on ${item}")
    }

    override fun onMeasurementTimelineMakeEntryClick(item: ScheduledMeasurementGroupUiModel) {
        viewModel.onAddEntry(scheduleMapper.toPresentation(item))
    }
}
