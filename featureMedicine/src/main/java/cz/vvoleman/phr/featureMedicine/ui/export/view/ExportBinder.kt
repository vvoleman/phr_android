package cz.vvoleman.phr.featureMedicine.ui.export.view

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicine.databinding.FragmentExportBinding
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState

class ExportBinder : BaseViewStateBinder<ExportViewState, FragmentExportBinding, ExportBinder.Notification>() {

    override fun bind(viewBinding: FragmentExportBinding, viewState: ExportViewState) {

    }

    sealed class Notification

    companion object {
        const val TAG = "ExportBinder"
    }

}