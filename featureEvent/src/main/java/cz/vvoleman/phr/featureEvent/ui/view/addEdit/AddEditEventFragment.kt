package cz.vvoleman.phr.featureEvent.ui.view.addEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureEvent.databinding.FragmentAddEditEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import cz.vvoleman.phr.featureEvent.presentation.viewmodel.AddEditEventViewModel
import cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.destination.AddEditEventDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditEventFragment :
    BaseFragment<AddEditEventViewState, AddEditEventNotification, FragmentAddEditEventBinding>() {

    override val viewModel: AddEditEventViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditEventDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditEventViewState, FragmentAddEditEventBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditEventBinding {
        return FragmentAddEditEventBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: AddEditEventNotification) {
        when (notification) {
            else -> {
                showSnackbar("Unknown notification: $notification")
            }
        }
    }
}
