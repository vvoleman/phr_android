package cz.vvoleman.phr.featureMeasurement.ui.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.DetailMeasurementGroupViewModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.destination.DetailMeasurementGroupDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMeasurementGroupFragment :
    BaseFragment<DetailMeasurementGroupViewState, DetailMeasurementGroupNotification, FragmentDetailMeasurementGroupBinding>() {

    override val viewModel: DetailMeasurementGroupViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailMeasurementGroupDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailMeasurementGroupBinding {
        return FragmentDetailMeasurementGroupBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: DetailMeasurementGroupNotification) {
        when (notification) {
            else -> {
                showSnackbar("Unhandled notification: $notification")
            }
        }
    }
}
