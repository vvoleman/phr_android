package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import cz.vvoleman.phr.BuildConfig
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentRecognizerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class RecognizerFragment : Fragment(R.layout.fragment_recognizer){

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
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecognizerBinding.bind(view)

        binding.apply {
            cameraButton.setOnClickListener {
                takePhotoWithIntent()
            }
        }

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        lifecycleScope.launchWhenStarted {
            viewModel.options.collect { options ->
                options ?: return@collect
                // Display Popup with options
                val popup = PopupMenu(requireContext(), binding.preview)
                popup.menu.add("Visit Date: ${options.visitDate}")
                popup.menu.add("Diagnose: ${options.diagnose}")
                popup.menu.add("Doctor: ${options.doctor}")
                popup.menu.add("Patient: ${options.patient}")
                popup.show()
            }
        }
    }

    private fun takePhotoWithIntent() {
        lifecycleScope.launchWhenStarted {
            getTempFileUri().let { uri ->
                latestTmpUri = uri
                takePictureContract.launch(uri)
            }

        }
    }

    private fun getTempFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".jpg", requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}