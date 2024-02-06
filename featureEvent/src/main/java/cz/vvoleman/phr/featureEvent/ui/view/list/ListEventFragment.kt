package cz.vvoleman.phr.featureEvent.ui.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.presentation.viewmodel.ListEventViewModel
import cz.vvoleman.phr.featureEvent.ui.mapper.list.destination.ListEventDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListEventFragment : BaseFragment<ListEventViewState, ListEventNotification, FragmentListEventBinding>() {

    override val viewModel: ListEventViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListEventDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListEventViewState, FragmentListEventBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListEventBinding {
        return FragmentListEventBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: ListEventNotification) {
        when(notification) {
            else -> {
                showSnackbar("Unknown notification")
            }
        }
    }
}
