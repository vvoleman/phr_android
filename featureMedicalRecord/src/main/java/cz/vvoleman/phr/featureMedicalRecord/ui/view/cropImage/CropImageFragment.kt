package cz.vvoleman.phr.featureMedicalRecord.ui.view.cropImage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentCropImageBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.CropImageViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.CropImageDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CropImageFragment : BaseFragment<CropImageViewState, CropImageNotification, FragmentCropImageBinding>() {

    override val viewModel: CropImageViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: CropImageDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<CropImageViewState, FragmentCropImageBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCropImageBinding {
        return FragmentCropImageBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val binder = viewStateBinder as CropImageBinder

        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is CropImageBinder.Notification.Crop -> {
                    viewModel.onCropImage(it.bitmap, it.quad)
                }
            }
        }
    }

    override fun handleNotification(notification: CropImageNotification) {
        when (notification) {
            else -> {
                showSnackbar(notification.toString())
            }
        }
    }
}
