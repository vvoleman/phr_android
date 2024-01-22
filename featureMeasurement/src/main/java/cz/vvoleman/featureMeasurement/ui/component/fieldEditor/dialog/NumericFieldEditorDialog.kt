package cz.vvoleman.featureMeasurement.ui.component.fieldEditor.dialog

import android.util.Log
import android.widget.ArrayAdapter
import cz.vvoleman.featureMeasurement.databinding.DialogNumericFieldEditorBinding
import cz.vvoleman.featureMeasurement.ui.model.core.field.NumericFieldUiModel
import cz.vvoleman.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel

class NumericFieldEditorDialog(
    private val unitGroups: List<UnitGroupUiModel>,
    listener: FieldEditorDialogListener,
    existingField: NumericFieldUiModel? = null,
) : FieldEditorDialog<DialogNumericFieldEditorBinding, NumericFieldUiModel>(listener, existingField) {

    override fun setupBinding(): DialogNumericFieldEditorBinding {
        return DialogNumericFieldEditorBinding.inflate(layoutInflater)
    }

    override fun getData(existingField: NumericFieldUiModel?): NumericFieldUiModel {
        val unitGroup = binding.spinnerUnitGroup.text.toString().let { unitGroupName ->
            unitGroups.find { it.name == unitGroupName }
        }

        val minimalValue = binding.editTextMinimalValue.text.toString().toDoubleOrNull()
        val maximalValue = binding.editTextMaximalValue.text.toString().toDoubleOrNull()

        if (minimalValue != null && maximalValue != null && minimalValue > maximalValue) {
            throw IllegalArgumentException("Minimal value cannot be greater than maximal value")
        }

        return NumericFieldUiModel(
            id = existingField?.id ?: System.currentTimeMillis().toString(),
            name = binding.editTextFieldName.text.toString(),
            unitGroup = unitGroup,
            minimalValue = binding.editTextMinimalValue.text.toString().toDoubleOrNull(),
            maximalValue = binding.editTextMaximalValue.text.toString().toDoubleOrNull(),
        )
    }

    override fun setValues(existingField: NumericFieldUiModel?) {
        Log.d("NumericFieldEditorDialog", "unitGroups: $unitGroups")
        if (existingField != null) {
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                unitGroups.map { it.name }
            )

            binding.apply {
                editTextFieldName.setText(existingField.name)
                spinnerUnitGroup.setAdapter(spinnerAdapter)
                spinnerUnitGroup.setText(existingField.unitGroup?.name)
                editTextMinimalValue.setText(existingField.minimalValue?.toString())
                editTextMaximalValue.setText(existingField.maximalValue?.toString())
            }
        }
    }
}
