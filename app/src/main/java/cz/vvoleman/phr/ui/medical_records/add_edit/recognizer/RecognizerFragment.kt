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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import cz.vvoleman.phr.BuildConfig
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentRecognizerBinding
import cz.vvoleman.phr.util.ocr.record.RecognizedOptions
import dagger.hilt.android.AndroidEntryPoint
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

    private val dialog: Dialog by lazy {
        Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_select_recognized_options)
        }
    }

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
        }

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        lifecycleScope.launchWhenStarted {
            viewModel.options.collect { options ->
                options ?: return@collect
                // Display Popup with options
                displaySelectOptionsDialog(options)
            }
        }
    }

    private fun displaySelectOptionsDialog(options: RecognizedOptions) {
        // create dialog with template
        val dialog = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_select_recognized_options)

            // set value to text view
            findViewById<Spinner>(R.id.diagnose_spinner).apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    options.diagnose.map { it.value }
                )
            }

            findViewById<Spinner>(R.id.date_spinner).apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    options.visitDate.map { it.toString() }
                )
            }

            findViewById<LinearLayout>(R.id.patient_layout).apply {
                this.visibility = if (options.patient.size > 1) View.VISIBLE else View.GONE
            }

            if (options.patient.size > 1) {
                findViewById<Spinner>(R.id.patient_spinner).apply {
                    adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        options.patient.map { it.value }
                    )
                }
            }

            findViewById<Button>(R.id.confirm_button).setOnClickListener {
                setFragmentResult(KEY_REQUEST, bundleOf(KEY_OPTIONS to viewModel.getSelectedOptions()))

            }

            findViewById<Button>(R.id.cancel_button).setOnClickListener {
                dialog.cancel()
            }

        }

        dialog.show()
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