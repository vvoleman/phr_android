package cz.vvoleman.phr.featureMedicine.ui.export.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.databinding.FragmentExportBinding
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportNotification
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import cz.vvoleman.phr.featureMedicine.presentation.export.viewmodel.ExportViewModel
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportDestinationMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExportFragment : BaseFragment<ExportViewState, ExportNotification, FragmentExportBinding>(){

    override val viewModel: ExportViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ExportDestinationMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ExportViewState, FragmentExportBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentExportBinding {
        return FragmentExportBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.buttonExport.setOnClickListener {
            viewModel.onExport()
        }
    }

    override fun handleNotification(notification: ExportNotification) {
        when(notification){
            is ExportNotification.CannotExport -> {
                showSnackbar("Nelze exportovat")
            }
        }
    }
}