package cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.base.BuildConfig
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectFileNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectFileViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.SelectFileViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.SelectFileDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.binder.SelectFileBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getTempFileUri().let { uri ->
                    latestTmpUri = uri
                    takePictureContract.launch(uri)
                }
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
