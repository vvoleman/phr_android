package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import cz.vvoleman.phr.BuildConfig
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentRecognizerBinding
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.dialog.OptionsDialogFragment
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.dialog.SelectedOptions
import cz.vvoleman.phr.util.ocr.record.RecognizedOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import java.io.File

@AndroidEntryPoint
class RecognizerFragment : Fragment(R.layout.fragment_recognizer) {

    companion object {
        val KEY_REQUEST = "RecognizedOptions"
        val KEY_OPTIONS = "SelectedOptions"
    }

    private val viewModel: RecognizerViewModel by viewModels()

    private var _binding: FragmentRecognizerBinding? = null
    private val binding get() = _binding!!

    private lateinit var recognizer: TextRecognizer
    private var latestTmpUri: Uri? = null
    private val takePictureContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                latestTmpUri?.let { uri ->
                    Log.d("MainActivity", "Image URI: $uri")
                    analyzeImage(uri)
                }
            }

        }

    private val getContentContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {uri ->
            uri?.let {
                analyzeImage(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecognizerBinding.bind(view)

        binding.apply {
            cameraButton.setOnClickListener {
                takePicture()
            }
            galleryButton.setOnClickListener {
                choosePicture()
            }
            optionButton.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    viewModel.options.first()?.let { options -> displaySelectOptionsDialog(options) }
                }
            }
        }

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        lifecycleScope.launchWhenStarted {
            viewModel.options.collect { options ->
                if (options == null) {
                    binding.optionButton.visibility = View.GONE
                    return@collect
                }
                binding.optionButton.visibility = View.VISIBLE
                // Display Popup with options
                displaySelectOptionsDialog(options)
            }
        }
    }

    private fun displaySelectOptionsDialog(options: RecognizedOptions) {
        val dialog = OptionsDialogFragment.newInstance(options)
        dialog.show(childFragmentManager, OptionsDialogFragment.TAG)

        requireActivity().supportFragmentManager.setFragmentResultListener(OptionsDialogFragment.REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            Log.d("RecognizerFragment", "Options selected")
            val selectedOptions = bundle.getParcelable<SelectedOptions>(OptionsDialogFragment.OPTIONS_KEY)
            selectedOptions?.let {
                setFragmentResult(KEY_REQUEST, bundleOf(KEY_OPTIONS to it))
                findNavController().popBackStack()
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

    private fun choosePicture(){
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
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    private fun analyzeImage(uri: Uri) {
        binding.preview.setImageURI(uri)
        recognizer.let {
            Log.d("MainActivity", "Starting text recognition")
            it.process(InputImage.fromFilePath(requireContext(), uri))
                .addOnSuccessListener { text ->
                    viewModel.setRawText(text)
                }
                .addOnFailureListener { e ->
                    Log.e("MainActivity", "Error processing image", e)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}