package cz.vvoleman.featureMeasurement.ui.component.fieldEditor.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupFieldUi

abstract class FieldEditorDialog<V : ViewBinding, T : MeasurementGroupFieldUi>(
    private val _listener: FieldEditorDialogListener,
    private val existingField: T? = null
) : DialogFragment() {

    private var _binding: V? = null
    protected val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = setupBinding()
        setValues(existingField)
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                try {
                    val data = getData(existingField)
                    _listener.onDialogSave(data)
                } catch (e: IllegalArgumentException) {
                    Log.e("FieldEditorDialog", "onCreateDialog: ", e)
                    return@setPositiveButton
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                dialog?.cancel()
            }
            .create()
    }

    protected abstract fun setupBinding(): V

    protected abstract fun getData(existingField: T?): T

    protected open fun setValues(existingField: T?) {}

    interface FieldEditorDialogListener {
        fun onDialogSave(data: MeasurementGroupFieldUi)
    }

}
