package cz.vvoleman.phr.featureMeasurement.ui.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.vvoleman.phr.base.ui.exception.PermissionDeniedException
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.export.exception.ExportFailedException
import cz.vvoleman.phr.common.ui.export.usecase.DocumentFactory
import cz.vvoleman.phr.common.ui.export.usecase.ExportPdfHelper
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.export.ExportDetailParamsPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.DetailMeasurementGroupViewModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupEntryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.destination.DetailMeasurementGroupDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.export.DetailMeasurementGroupPage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailMeasurementGroupFragment :
    BaseFragment<DetailMeasurementGroupViewState, DetailMeasurementGroupNotification, FragmentDetailMeasurementGroupBinding>() {

    override val viewModel: DetailMeasurementGroupViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailMeasurementGroupDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding>

    @Inject
    lateinit var entryMapper: MeasurementGroupEntryUiModelToPresentationMapper

    @Inject
    lateinit var entryInfoMapper: EntryInfoUiModelToMeasurementGroupMapper

    private var _exportPdfHelper: ExportPdfHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createFileLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri ->
            lifecycleScope.launch {
                kotlin.runCatching { _exportPdfHelper?.handleCreateFileResult(uri) }.getOrElse {
                    showSnackbar("Nepodařilo se vytvořit soubor: ${it.message}")
                }
            }
        }

        val permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                _exportPdfHelper?.handlePermissionResult(permissions)
            }

        _exportPdfHelper = ExportPdfHelper(
            requireContext(),
            createFileLauncher,
            permissionsLauncher
        )
    }

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailMeasurementGroupBinding {
        return FragmentDetailMeasurementGroupBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val binder = viewStateBinder as DetailMeasurementGroupBinder
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is DetailMeasurementGroupBinder.Notification.ShowTableItemOptionsMenu -> {
                    showTableOptions(it.item, it.anchorView)
                }
            }
        }

        binding.buttonAddEntry.setOnClickListener {
            viewModel.onAddEntry()
        }
        binding.buttonExport.setOnClickListener {
            viewModel.onExport()
        }
    }

    override fun setOptionsMenu(): Int {
        return R.menu.options_detail_measurement_group
    }

    override fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.action_add -> {
                viewModel.onAddEntry()
                true
            }
            else -> super.onOptionsMenuItemSelected(menuItem)
        }
    }

    override fun handleNotification(notification: DetailMeasurementGroupNotification) {
        when (notification) {
            is DetailMeasurementGroupNotification.Export -> {
                val params = ExportDetailParamsPresentationModel(
                    measurementGroup = notification.measurementGroup,
                    entries = entryInfoMapper.toUi(notification.measurementGroup)
                )
                launchExport(_exportPdfHelper!!, params)
            }
        }
    }

    private fun showTableOptions(item: EntryInfoUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_detail_table_item)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                cz.vvoleman.phr.common_datasource.R.id.action_edit -> {
                    showSnackbar("Edit ${item.entry.createdAt}")
                    true
                }
                cz.vvoleman.phr.common_datasource.R.id.action_delete -> {
                    showDeleteConfirmDialog(item)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    private fun showDeleteConfirmDialog(item: EntryInfoUiModel) {
        showConfirmDialog(
            title = R.string.dialog_delete_entry_title,
            message = R.string.dialog_delete_entry_message,
            positiveAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_delete) {
                viewModel.onDeleteEntry(entryMapper.toPresentation(item.entry))
            },
            negativeAction = Pair(cz.vvoleman.phr.common_datasource.R.string.action_cancel) {
                it.dismiss()
            }
        )
    }

    private fun launchExport(
        helper: ExportPdfHelper,
        params: ExportDetailParamsPresentationModel
    ) {
        try {
            DocumentFactory(
                helper, splitEntriesToPages(params)
            ).generate()
        } catch (e: PermissionDeniedException) {
            showSnackbar("Aplikace nemá oprávnění k přístupu k uložišti: ${e.message}")
            viewModel.onPermissionDenied(helper.hasPermissions())
        } catch (_: ExportFailedException) {
            showSnackbar("Nepodařilo se exportovat do ${helper.exportType}")
        } catch (e: Throwable) {
            showSnackbar("Vyskytla se neočekávaná chyba: ${e.message}")
        }
    }

    private fun splitEntriesToPages(params: ExportDetailParamsPresentationModel): List<DetailMeasurementGroupPage> {
        val pages = mutableListOf<DetailMeasurementGroupPage>()
        val entries = params.entries.sortedBy { it.entry.createdAt }
        val pageSize = 11
        val pageCount = entries.size / pageSize + 1
        for (i in 0 until pageCount) {
            val start = i * pageSize
            val end = (i + 1) * pageSize
            val subEntries = entries.subList(start, end.coerceAtMost(entries.size))
            val subParams = params.copy(entries = subEntries)
            pages.add(DetailMeasurementGroupPage(subParams))
        }
        return pages
    }
}
