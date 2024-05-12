package cz.vvoleman.phr.featureEvent.ui.view.addEdit

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel
import cz.vvoleman.phr.common.utils.checkVisibility
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.featureEvent.databinding.FragmentAddEditEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import cz.vvoleman.phr.featureEvent.ui.adapter.addEdit.ReminderAdapter
import cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.ReminderUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.model.addEdit.ReminderUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class AddEditEventBinder(
    private val reminderMapper: ReminderUiModelToPresentationMapper,
    private val workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper,
) :
    BaseViewStateBinder<AddEditEventViewState, FragmentAddEditEventBinding, AddEditEventBinder.Notification>(),
    ReminderAdapter.ReminderListener {

    private var reminderAdapter: ReminderAdapter? = null

    override fun firstBind(viewBinding: FragmentAddEditEventBinding, viewState: AddEditEventViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.editTextName.setText(viewState.name)
        viewBinding.editTextDescription.setText(viewState.description)
        viewState.date?.let { viewBinding.datePickerStartAt.setDate(it) }
        viewState.time?.let { viewBinding.timePickerStartAt.setTime(it) }

        reminderAdapter = ReminderAdapter(this)
        viewBinding.recyclerViewReminder.apply {
            adapter = reminderAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        viewBinding.switchReminder.apply {
            isChecked = viewState.areRemindersEnabled
            setOnCheckedChangeListener { _, _ ->
                notify(Notification.AllRemindersToggle)
            }
        }

        viewBinding.editTextName.textChanges()
            .debounce(300)
            .onEach {
                Log.d("AddEditEventBinder", "name changes: $it")
                notify(Notification.FieldDataChanged(name = it.toString()))
            }.launchIn(lifecycleScope)

        viewBinding.editTextDescription.textChanges()
            .debounce(300)
            .onEach {
                notify(Notification.FieldDataChanged(description = it.toString()))
            }.launchIn(lifecycleScope)

        viewBinding.spinnerMedicalWorker.apply {
            val items = workerMapper.toUi(viewState.workers)
            setAdapter(
                ArrayAdapter(
                    fragmentContext,
                    cz.vvoleman.phr.common_datasource.R.layout.item_default,
                    items
                )
            )
            setOnItemClickListener { _, _, position, _ ->
                val item = adapter.getItem(position) as SpecificMedicalWorkerUiModel
                notify(Notification.FieldDataChanged(worker = item))
            }

            if (viewState.selectedWorker != null) {
                val item = items.first { it.id == viewState.selectedWorker.id }
                setText(item.toString(), false)
            }
        }

        reminderAdapter?.submitList(reminderMapper.toUi(viewState.reminders))
    }

    override fun bind(viewBinding: FragmentAddEditEventBinding, viewState: AddEditEventViewState) {
        super.bind(viewBinding, viewState)

        reminderAdapter?.submitList(reminderMapper.toUi(viewState.reminders))

        viewBinding.recyclerViewReminder.visibility = if (viewState.areRemindersEnabled) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (viewState.date != null && viewState.time != null) {
            val date = viewState.date.atTime(viewState.time)

            val isVisible = date.isAfter(LocalDateTime.now())

            viewBinding.layoutReminder.visibility = checkVisibility(isVisible)
            viewBinding.textViewInPast.visibility = checkVisibility(!isVisible)
        }
    }

    override fun onDestroy(viewBinding: FragmentAddEditEventBinding) {
        reminderAdapter = null

        super.onDestroy(viewBinding)
    }

    sealed class Notification {
        data class ReminderClick(val reminder: ReminderUiModel) : Notification()
        object AllRemindersToggle : Notification()
        data class FieldDataChanged(
            val name: String? = null,
            val description: String? = null,
            val worker: SpecificMedicalWorkerUiModel? = null,
        ) : Notification()
    }

    override fun onReminderClick(reminder: ReminderUiModel) {
        notify(Notification.ReminderClick(reminder))
    }
}
