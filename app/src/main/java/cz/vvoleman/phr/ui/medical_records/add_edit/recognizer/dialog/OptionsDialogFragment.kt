package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.DialogSelectRecognizedOptionsBinding
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.RecognizerFragment
import cz.vvoleman.phr.util.ocr.record.RecognizedOptions

class OptionsDialogFragment : DialogFragment() {

    val viewModel: OptionsDialogViewModel by viewModels()

    private var _binding: DialogSelectRecognizedOptionsBinding? = null
    val binding get() = _binding!!

    private val diagnoseSelected = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectDiagnose(position)
        }
    }

    private val dateSelected = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectDate(position)
        }
    }

    private val patientSelected = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectPatient(position)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSelectRecognizedOptionsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        val options = arguments?.getParcelable<RecognizedOptions>("options")
        viewModel.options.value = options
        if (options == null) {
            dismiss()
            return builder.create()
        }
        binding.apply {
            diagnoseSpinner.apply {
                onItemSelectedListener = diagnoseSelected
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    options.diagnose.map { it.value }
                )
            }

            dateSpinner.apply {
                onItemSelectedListener = dateSelected
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    options.visitDate.map { it.value }
                )
            }

            patientLayout.visibility = if (options.patient.size > 1) View.VISIBLE else View.GONE

            if (options.patient.size > 1) {
                patientSpinner.apply {
                    onItemSelectedListener = patientSelected
                    adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        options.patient.map { it.value }
                    )
                }
            }

            confirmButton.setOnClickListener {
                Log.d(TAG, "onCreateDialog: confirm")
                confirm()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return builder.create()
    }

    private fun confirm() {

        val options = viewModel.getSelectedOptions()
        Log.d(TAG, "confirm: $options")
        val result = bundleOf(OPTIONS_KEY to options)
        requireActivity().supportFragmentManager.setFragmentResult(REQUEST_KEY, result)

        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "request_key"
        const val OPTIONS_KEY = "options"
        const val TAG = "OptionsDialogFragment"

        fun newInstance(options: RecognizedOptions) :OptionsDialogFragment {
            val fragment = OptionsDialogFragment()
            val args = Bundle()
            args.putParcelable("options", options)
            fragment.arguments = args
            return fragment
        }
    }

}