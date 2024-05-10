package cz.vvoleman.phr.featureEvent.ui.view.list

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.presentation.viewmodel.ListEventViewModel
import cz.vvoleman.phr.featureEvent.ui.mapper.core.EventUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.mapper.list.destination.ListEventDestinationUiMapper
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel
import cz.vvoleman.phr.featureEvent.ui.usecase.ExportEventsToCalendarUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListEventFragment : BaseFragment<ListEventViewState, ListEventNotification, FragmentListEventBinding>() {

    override val viewModel: ListEventViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListEventDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListEventViewState, FragmentListEventBinding>

    @Inject
    lateinit var exportUseCase: ExportEventsToCalendarUseCase

    @Inject
    lateinit var eventMapper: EventUiModelToPresentationMapper

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.onExportEvents()
            } else {
                showSnackbar("Nelze exportovat plány do kalendáře. Povolení bylo zamítnuto.")
            }
        }

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListEventBinding {
        return FragmentListEventBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.fabAddEvent.setOnClickListener {
            viewModel.onAddEvent()
        }

        val binder = viewStateBinder as ListEventBinder
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is ListEventBinder.Notification.OnOptionsMenuPopup -> {
                    showPopupMenu(it.item, it.view)
                }
                ListEventBinder.Notification.LoadOlderEvents -> {
                    viewModel.onLoadOlderEvents()
                }
                is ListEventBinder.Notification.ToggleShowAll -> {
                    viewModel.onToggleShowAll(it.isToggle)
                }
            }
        }
    }

    private fun showPopupMenu(item: EventUiModel, view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.options_event_item)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel.onEditEvent(item.id)
                    true
                }
                R.id.action_delete -> {
                    showConfirmDialog(
                        title = R.string.delete_event_title,
                        message = R.string.delete_event_message,
                        positiveAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_delete) {
                            viewModel.onDeleteEvent(eventMapper.toPresentation(item))
                        },
                        negativeAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_cancel) {}
                    )
                    true
                }
                else -> super.onOptionsMenuItemSelected(it)
            }
        }

        popup.show()
    }

    override fun setOptionsMenu(): Int {
        return R.menu.options_list_event
    }

    override fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_export -> {
                showConfirmDialog(
                    title = "Export plánů",
                    message = "Přejete si exportovat plány do kalendáře?",
                    positiveAction = Pair("Ano") {
                        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_CALENDAR)
                    },
                    negativeAction = Pair("Ne") {}
                )
                return true
            }
            else -> {
                return super.onOptionsMenuItemSelected(menuItem)
            }
        }
    }

    override fun handleNotification(notification: ListEventNotification) {
        when(notification) {
            is ListEventNotification.ExportEvents -> {
                lifecycleScope.launch {
                    try {
                        exportUseCase.execute(eventMapper.toUi(notification.events))
                    } catch (e: Exception) {
                        showSnackbar(R.string.error_export_events)
                    }
                }
            }
            is ListEventNotification.EventDeleted -> {
                showSnackbar(
                    message = R.string.event_deleted,
                    actions = listOf(
                        Pair(getString(cz.vvoleman.phr.common_datasource.R.string.action_undo)) {
                            viewModel.onUndoDeleteEvent(notification.event)
                        }
                    )
                )
            }
        }
    }
}
