package cz.vvoleman.phr.ui.medical_records.add_edit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ComponentActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.diagnose.DiagnoseWithGroup
import cz.vvoleman.phr.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.RecognizerFragment
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.RecognizerViewModel
import cz.vvoleman.phr.ui.views.date_picker.DatePicker
import cz.vvoleman.phr.ui.views.dialog_spinner.DialogSpinner
import cz.vvoleman.phr.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class AddEditMedicalRecordFragment :
    Fragment(R.layout.fragment_add_edit_medical_record), DialogSpinner.DialogSpinnerListener,
    DatePicker.DatePickerListener {

    private val TAG = "AddEditMedicalRecordFragment"

    private val viewModel: AddEditMedicalRecordViewModel by viewModels()

    private var _binding: FragmentAddEditMedicalRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(RecognizerFragment.KEY_REQUEST) { key, bundle ->
            Log.d(TAG, "Received result from RecognizerFragment")

            val options =
                bundle.getParcelable<RecognizerViewModel.SelectedOptions>(RecognizerFragment.KEY_OPTIONS)

            Log.d(TAG, options.toString())

            if (options == null) return@setFragmentResultListener

            viewModel.setRecognizedOptions(options)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddEditMedicalRecordBinding.bind(view)

        binding.apply {
            datePicker.setDate(viewModel.recordDate.value)
            datePicker.setListener(this@AddEditMedicalRecordFragment)

            editTextRecordText.addTextChangedListener {
                viewModel.recordText = it.toString()
            }

            buttonActionCamera.setOnClickListener {
                val action =
                    AddEditMedicalRecordFragmentDirections.actionAddEditMedicalRecordToRecognizerFragment()
                findNavController().navigate(action)
            }

            buttonSave.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.onSaveClick()
                }
            }

            dialogSpinnerDiagnose.setListener(this@AddEditMedicalRecordFragment)
        }

        viewModel.allDiagnoses.observe(viewLifecycleOwner) { diagnoses ->
            Log.d(TAG, "Diagnoses: $diagnoses")
            val pairs = diagnoses.map {
                it.getAdapterPair()
            }
            Log.d(TAG, "allDiagnoses collected: $pairs")
            viewLifecycleOwner.lifecycleScope.launch {
                binding.dialogSpinnerDiagnose.setData(pairs)
            }
        }
        viewModel.selectedDiagnose.asLiveData().observe(viewLifecycleOwner) { diagnose ->
            if (diagnose != null) {
                Log.d(TAG, "Selected diagnose: ${diagnose.diagnose}")
                binding.dialogSpinnerDiagnose.setSelectedItem(diagnose.diagnose.getAdapterPair())
            }
        }

        collectLatest(viewModel.recordDate) {
            binding.datePicker.setDate(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.medicalRecordsEvent.collect { event ->
                when (event) {
                    is AddEditMedicalRecordViewModel.MedicalRecordEvent.NavigateBackWithResult -> {
                        setFragmentResult(
                            requestKey = "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditMedicalRecordViewModel.MedicalRecordEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditMedicalRecordViewModel.MedicalRecordEvent.NetworkError -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditMedicalRecordViewModel.MedicalRecordEvent.DifferentPatientSelected -> {
                        Snackbar.make(
                            requireView(),
                            "Lékařská zpráva se váže k jinému pacientovi než je aktuálně vybrán",
                            Snackbar.LENGTH_LONG
                        ).setAction("Zrušit") {
                            viewModel.patient = null
                        }
                    }
                }.exhaustive
            }

            val firstDiagnose = viewModel.selectedDiagnose.first()
            if (firstDiagnose != null) {
                binding.dialogSpinnerDiagnose.setSelectedItem(firstDiagnose.diagnose.getAdapterPair())
            }

        }
    }

    override fun onItemSelected(item: AdapterPair): Boolean {
        viewModel.selectedDiagnose.value = item.objectValue as DiagnoseWithGroup
        Log.d(TAG, "onItemSelected: $item")

        return true
    }

    override fun onSearch(query: String) {
        viewModel.diagnoseSearchQuery.value = query
    }

    override fun onDateSelected(date: LocalDate) {
        viewModel.setDate(date)
    }
}

fun <T> Fragment.collectLatest(
    flow: Flow<T>,
    action: suspend (T) -> Unit
) {
    lifecycleScope.launchWhenStarted {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}