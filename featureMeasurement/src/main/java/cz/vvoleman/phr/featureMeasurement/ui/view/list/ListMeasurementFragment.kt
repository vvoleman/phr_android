package cz.vvoleman.phr.featureMeasurement.ui.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.ListMeasurementViewModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMeasurementFragment :
    BaseFragment<ListMeasurementViewState, ListMeasurementNotification, FragmentListMeasurementBinding>() {

    override val viewModel: ListMeasurementViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListMeasurementDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListMeasurementBinding {
        return FragmentListMeasurementBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.fabAddMeasurementGroup.setOnClickListener {
            viewModel.onAddMeasurementGroup()
        }
    }

    override fun handleNotification(notification: ListMeasurementNotification) {
        when (notification) {
            else -> {
            }
        }
    }
}