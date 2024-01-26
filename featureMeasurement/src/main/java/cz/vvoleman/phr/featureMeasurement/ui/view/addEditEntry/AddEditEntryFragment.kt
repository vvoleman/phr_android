package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditEntryBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.AddEditEntryViewModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.destination.AddEditEntryDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditEntryFragment :
    BaseFragment<AddEditEntryViewState, AddEditEntryNotification, FragmentAddEditEntryBinding>() {

    override val viewModel: AddEditEntryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditEntryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditEntryViewState, FragmentAddEditEntryBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditEntryBinding {
        return FragmentAddEditEntryBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()


    }

    override fun handleNotification(notification: AddEditEntryNotification) {
        when (notification) {
            else -> {
                showSnackbar("Notifications are not implemented yet")
            }
        }
    }
}
