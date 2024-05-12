package cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.DetailMedicalWorkerViewModel
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.DetailMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailMedicalWorkerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMedicalWorkerFragment :
    BaseFragment<DetailMedicalWorkerViewState, DetailMedicalWorkerNotification, FragmentDetailMedicalWorkerBinding>() {

    override val viewModel: DetailMedicalWorkerViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailMedicalWorkerDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
        ViewStateBinder<DetailMedicalWorkerViewState, FragmentDetailMedicalWorkerBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailMedicalWorkerBinding {
        return FragmentDetailMedicalWorkerBinding.inflate(inflater, container, false)
    }

    override fun setOptionsMenu(): Int {
        return R.menu.options_detail_worker
    }

    override fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_edit -> {
                viewModel.onEdit()
                true
            }
            else -> super.onOptionsMenuItemSelected(menuItem)
        }
    }

    override fun handleNotification(notification: DetailMedicalWorkerNotification) {
        when (notification) {
            else -> {
                showSnackbar("Not yet implemented: $notification")
            }
        }
    }
}
