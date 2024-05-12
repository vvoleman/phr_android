package cz.vvoleman.phr.featureMeasurement.ui.component.fieldEditor.dialog

import android.util.Log
import android.widget.ArrayAdapter
import cz.vvoleman.phr.featureMeasurement.databinding.DialogNumericFieldEditorBinding
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.NumericFieldUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel

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

        require(minimalValue != null && maximalValue != null && minimalValue > maximalValue) {
            "Minimal value or maximal value must be set"
        }

        require(unitGroup != null) {
            "Unit group must be set"
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
                cz.vvoleman.phr.common_datasource.R.layout.item_default,
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
