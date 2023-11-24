package cz.vvoleman.phr.common.ui.view.healthcare.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.ListHealthcareViewModel
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.ListHealthcareDestinationUiMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListHealthcareFragment :
    BaseFragment<ListHealthcareViewState, ListHealthcareNotification, FragmentListHealthcareBinding>() {
    override val viewModel: ListHealthcareViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListHealthcareDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding>
    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListHealthcareBinding {
        return FragmentListHealthcareBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: ListHealthcareNotification) {
        TODO("Not yet implemented")
    }
}
