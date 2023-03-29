package cz.vvoleman.phr.common.ui.view.listpatients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsNotification
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.presentation.viewmodel.ListPatientsViewModel
import cz.vvoleman.phr.common.ui.mapper.destination.ListPatientsDestinationUiMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListPatientsFragment :
    BaseFragment<ListPatientsViewState, ListPatientsNotification, FragmentListPatientsBinding>() {


    override val viewModel: ListPatientsViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListPatientsDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListPatientsViewState, FragmentListPatientsBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListPatientsBinding {
        return FragmentListPatientsBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: ListPatientsNotification) {
        when (notification) {
            is ListPatientsNotification.PatientAdded -> TODO()
            is ListPatientsNotification.PatientDeleted -> TODO()
            is ListPatientsNotification.PatientEdited -> TODO()
            is ListPatientsNotification.PatientSelected -> TODO()
        }
    }
}