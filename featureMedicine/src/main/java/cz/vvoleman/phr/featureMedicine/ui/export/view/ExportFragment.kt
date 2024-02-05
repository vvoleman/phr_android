package cz.vvoleman.phr.featureMedicine.ui.export.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.vvoleman.phr.base.ui.exception.PermissionDeniedException
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.domain.model.export.ExportType
import cz.vvoleman.phr.common.ui.export.exception.ExportFailedException
import cz.vvoleman.phr.common.ui.export.usecase.DocumentFactory
import cz.vvoleman.phr.common.ui.export.usecase.ExportPdfHelper
import cz.vvoleman.phr.common.utils.toLocalDateTime
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentExportBinding
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportNotification
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportParamsPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import cz.vvoleman.phr.featureMedicine.presentation.export.viewmodel.ExportViewModel
import cz.vvoleman.phr.featureMedicine.ui.export.adapter.ExportAdapter
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportDestinationMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExportFragment :
    BaseFragment<ExportViewState, ExportNotification, FragmentExportBinding>() {

    override val viewModel: ExportViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ExportDestinationMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ExportViewState, FragmentExportBinding>

    private var _exportPdfHelper: ExportPdfHelper? = null

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentExportBinding {
        return FragmentExportBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createFileLauncher = registerForActivityResult(CreateDocument("application/pdf")) { uri ->
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
            R.layout.document_test_pdf,
            requireContext(),
            createFileLauncher,
            permissionsLauncher
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)

        (viewStateBinder as ExportBinder).setAdapter(ExportAdapter())

        return binding
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.buttonExport.setOnClickListener {
            viewModel.onExport()
        }

        binding.buttonPreview.setOnClickListener {
            viewModel.onPreview()
        }

//        binding.spinnerExport.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }

        binding.editTextStartAt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val datetime = s.toString().toLocalDateTime()
                if (datetime != null) {
                    viewModel.onStartAtChanged(datetime)
                } else {
                    showSnackbar("Neplatné datum: $s")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after text changes
            }
        })

        binding.editTextEndAt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val datetime = s.toString().toLocalDateTime()
                if (datetime != null) {
                    viewModel.onEndAtChanged(datetime)
                } else {
                    showSnackbar("Neplatné datum: $s")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after text changes
            }
        })
    }

    override fun handleNotification(notification: ExportNotification) {
        when (notification) {
            is ExportNotification.CannotExport -> {
                showSnackbar("Nelze exportovat")
            }

            is ExportNotification.CannotLoadData -> {
                showSnackbar("Nelze načíst data")
            }

            is ExportNotification.ExportAs -> {
                when (notification.type) {
                    ExportType.PDF -> {
                        launchExport(_exportPdfHelper!!, notification.params)
                    }

                    ExportType.CSV -> {
                        showSnackbar("CSV není podporováno")
                    }
                }
            }
        }
    }

    private fun launchExport(
        helper: ExportPdfHelper,
        params: ExportParamsPresentationModel
    ) {
        try {
            DocumentFactory(
                helper, listOf(
                    MedicineExportPage(params)
                )
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
}
