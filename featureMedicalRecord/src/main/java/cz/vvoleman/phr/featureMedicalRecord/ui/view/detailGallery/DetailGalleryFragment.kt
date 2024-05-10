package cz.vvoleman.phr.featureMedicalRecord.ui.view.detailGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailGalleryBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery.DetailGalleryNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery.DetailGalleryViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.DetailGalleryViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.DetailGalleryDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailGalleryFragment :
    BaseFragment<DetailGalleryViewState, DetailGalleryNotification, FragmentDetailGalleryBinding>() {

    override val viewModel: DetailGalleryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailGalleryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailGalleryViewState, FragmentDetailGalleryBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailGalleryBinding {
        return FragmentDetailGalleryBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: DetailGalleryNotification) {
        when (notification) {
            else -> {
                showSnackbar("Notification $notification not handled")
            }
        }
    }
}
