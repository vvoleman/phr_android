package cz.vvoleman.phr.common.ui.view.problemCategory.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.model.problemCategory.export.ProblemCategoryParams
import cz.vvoleman.phr.common.presentation.viewmodel.problemCategory.DetailProblemCategoryViewModel
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.ui.export.usecase.ExportPdfHelper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.DetailProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.ui.view.BaseExportFragment
import cz.vvoleman.phr.common.ui.view.problemCategory.export.ProblemCategoryDocumentPage
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailProblemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailProblemCategoryFragment :
    BaseExportFragment<DetailProblemCategoryViewState, DetailProblemCategoryNotification, FragmentDetailProblemCategoryBinding>() {

    override val viewModel: DetailProblemCategoryViewModel by viewModels()

    private var _exportPdfHelper: ExportPdfHelper? = null

    @Inject
    override lateinit var destinationMapper: DetailProblemCategoryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailProblemCategoryViewState, FragmentDetailProblemCategoryBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createFileLauncher =
            registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri ->
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

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailProblemCategoryBinding {
        return FragmentDetailProblemCategoryBinding.inflate(inflater, container, false)
    }

    override fun setOptionsMenu(): Int {
        return R.menu.options_problem_category_detail
    }

    override fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_export -> {
                viewModel.onExport()
                true
            }

            else -> {
                super.onOptionsMenuItemSelected(menuItem)
            }
        }
    }

    override fun handleNotification(notification: DetailProblemCategoryNotification) {
        when (notification) {
            is DetailProblemCategoryNotification.ExportPdf -> {
                val pages = mutableListOf(getTitlePage(notification.params))
                pages.addAll(notification.pages)
                launchExport(pages)
            }
            is DetailProblemCategoryNotification.ExportEmpty -> {
                showSnackbar(R.string.error_no_data_to_export)
            }
        }
    }

    private fun getTitlePage(params: ProblemCategoryParams): DocumentPage {
        return ProblemCategoryDocumentPage(params)
    }
}
