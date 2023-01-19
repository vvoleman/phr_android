package cz.vvoleman.phr.ui.medical_records.add_edit

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditMedicalRecordFragment : Fragment(R.layout.fragment_add_edit_medical_record) {

    private val viewModel: AddEditMedicalRecordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditMedicalRecordBinding.bind(view)

        binding.apply {
            editTextDate.setText(viewModel.recordDate)
            editTextRecordText.setText(viewModel.recordText)

            editTextDate.addTextChangedListener {
                viewModel.recordDate = it.toString()
            }

            editTextRecordText.addTextChangedListener {
                viewModel.recordText = it.toString()
            }

            buttonSave.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.medicalRecordsEvent.collect { event->
                when(event) {
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
                }.exhaustive
            }
        }
    }

}