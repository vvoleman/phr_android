package cz.vvoleman.featureMeasurement.ui.view.addEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.featureMeasurement.presentation.viewmodel.AddEditMeasurementViewModel
import cz.vvoleman.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMeasurementFragment :
    BaseFragment<AddEditMeasurementViewState, AddEditMeasurementNotification, FragmentAddEditMeasurementBinding>() {

    override val viewModel: AddEditMeasurementViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditMeasurementDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditMeasurementBinding {
        return FragmentAddEditMeasurementBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: AddEditMeasurementNotification) {
        when (notification) {
            else -> {
            }
        }
    }
}
