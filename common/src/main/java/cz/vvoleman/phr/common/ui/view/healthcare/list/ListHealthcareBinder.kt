package cz.vvoleman.phr.common.ui.view.healthcare.list

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding

class ListHealthcareBinder : BaseViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding, ListHealthcareNotification>() {
    override fun bind(viewBinding: FragmentListHealthcareBinding, viewState: ListHealthcareViewState) {
        Log.d("ListHealthcareBinder", "bind")
    }
}
