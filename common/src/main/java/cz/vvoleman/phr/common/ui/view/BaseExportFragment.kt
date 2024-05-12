package cz.vvoleman.phr.common.ui.view

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.base.ui.exception.PermissionDeniedException
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.export.exception.ExportFailedException
import cz.vvoleman.phr.common.ui.export.usecase.DocumentFactory
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.ui.export.usecase.ExportPdfHelper
import kotlinx.coroutines.launch

abstract class BaseExportFragment<VIEW_STATE : Any, NOTIFICATION : Any, VIEW_BINDING : ViewBinding> :
    BaseFragment<VIEW_STATE, NOTIFICATION, VIEW_BINDING>() {

    protected var exportPdfHelper: ExportPdfHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupExportHelper()
    }

    protected open fun setupExportHelper() {
        val createFileLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument("application/pdf")
        ) { uri ->
            lifecycleScope.launch {
                kotlin.runCatching {
                    exportPdfHelper?.handleCreateFileResult(uri)
                    showSnackbar("Soubor byl úspěšně vytvořen")
                }.getOrElse {
                    showSnackbar("Nepodařilo se vytvořit soubor: ${it.message}")
                }
            }
        }

        val permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                exportPdfHelper?.handlePermissionResult(permissions)
            }

        exportPdfHelper = ExportPdfHelper(
            requireContext(),
            createFileLauncher,
            permissionsLauncher
        )
    }

    protected fun launchExport(
        pages: List<DocumentPage>
    ) {
        val helper = exportPdfHelper
        require(helper != null) { "Export helper is not initialized" }

        try {
            DocumentFactory(
                helper,
                pages
            ).generate()
        } catch (e: PermissionDeniedException) {
            showSnackbar("Aplikace nemá oprávnění k přístupu k uložišti: ${e.message}")
        } catch (_: ExportFailedException) {
            showSnackbar("Nepodařilo se exportovat do ${helper.exportType}")
        } catch (e: Throwable) {
            showSnackbar("Vyskytla se neočekávaná chyba: ${e.message}")
        }
    }
}
