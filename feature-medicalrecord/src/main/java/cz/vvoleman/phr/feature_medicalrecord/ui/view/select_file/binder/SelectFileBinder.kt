package cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.binder

import android.view.View
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileViewState

class SelectFileBinder :
    BaseViewStateBinder<SelectFileViewState, FragmentSelectFileBinding, SelectFileBinder.Notification>() {


    override fun bind(viewBinding: FragmentSelectFileBinding, viewState: SelectFileViewState) {
        viewBinding.progress.visibility = if (viewState.isLoading()) View.VISIBLE else View.INVISIBLE
    }

    sealed class Notification {
        object TakePhoto : Notification()
        object SelectFile : Notification()
    }

}