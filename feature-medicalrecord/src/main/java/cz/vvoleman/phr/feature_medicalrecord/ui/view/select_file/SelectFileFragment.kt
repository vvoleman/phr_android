package cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.base.BuildConfig
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicalrecord.R
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.viewmodel.SelectFileViewModel
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.SelectFileDestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.binder.SelectFileBinder
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SelectFileFragment : BaseFragment<SelectFileViewState, SelectFileNotification, FragmentSelectFileBinding>() {

    override val viewModel: SelectFileViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: SelectFileDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<SelectFileViewState, FragmentSelectFileBinding>

    private val takePictureContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                latestTmpUri?.let { uri ->
                    val image = InputImage.fromFilePath(requireContext(), uri)
                    viewModel.onRunImageAnalyze(image, uri)
                }
            }
        }

    private val getContentContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val image = InputImage.fromFilePath(requireContext(), uri)
                viewModel.onRunImageAnalyze(image, uri)
            }
        }

    private var latestTmpUri: Uri? = null

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectFileBinding {
        return FragmentSelectFileBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.apply {
            cancelButton.setOnClickListener { viewModel.onCancel() }
            takePhotoButton.setOnClickListener { takePicture() }
            attachFileButton.setOnClickListener { choosePicture() }
            confirmButton.setOnClickListener { viewModel.onStartSelectingOptions() }
        }

        val binder = (viewStateBinder as SelectFileBinder)

        collectLifecycleFlow(binder.notification) {
            when (it) {
                is SelectFileBinder.Notification.ConfirmWithOptions -> {
                    viewModel.onConfirmWithOptions(it.diagnose, it.visitDate, it.patient)
                }
                SelectFileBinder.Notification.ConfirmWithoutOptions -> {
                    viewModel.onConfirmWithoutOptions()
                }
            }
        }
    }

    override fun handleNotification(notification: SelectFileNotification) {
        when (notification) {
            is SelectFileNotification.Error -> {
                Snackbar.make(binding.root, getText(R.string.select_file_error), Snackbar.LENGTH_LONG).show()
            }
            SelectFileNotification.OptionsRecognized -> {
                (viewStateBinder as SelectFileBinder).openDialog()
            }
        }
    }

    private fun takePicture() {
        lifecycleScope.launchWhenStarted {
            getTempFileUri().let { uri ->
                latestTmpUri = uri
                takePictureContract.launch(uri)
            }
        }
    }

    private fun choosePicture() {
        lifecycleScope.launchWhenStarted {
            getContentContract.launch("image/*")
        }
    }

    private fun getTempFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".jpg", requireContext().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.LIBRARY_PACKAGE_NAME}.provider",
            tmpFile
        )
    }
}
