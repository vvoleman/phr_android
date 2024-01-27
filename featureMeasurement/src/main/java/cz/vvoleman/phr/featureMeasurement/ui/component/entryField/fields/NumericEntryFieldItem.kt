package cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields

import android.content.res.Resources
import android.util.Log
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemNumericFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NumericEntryFieldItem(
    private val label: String,
    private val value: Number,
    private val minimalValue: Number?,
    private val maximalValue: Number?,
    id: String,
    coroutineScope: CoroutineScope,
    listener: EntryFieldItemListener,
) : EntryFieldItem(id, coroutineScope, listener) {
    override fun getType(): Type {
        return Type.NUMERIC
    }

    override fun initField(binding: ViewBinding) {
        val numericBinding = binding as ItemNumericFieldEntryBinding

        Log.d("NumericEntryField", "initField: $label")
        numericBinding.editText.textChanges(false)
            .debounce(150)
            .onEach {
                val error = getErrorMessage(it.toString(), numericBinding.root.resources)
                numericBinding.textInputLayout.error = error
                listener?.onValueChanged(id, it.toString())
            }.launchIn(coroutineScope)
    }

    override fun isValid(value: String?): Boolean {
        val number = value?.toDoubleOrNull() ?: return false

        if (minimalValue != null && number < minimalValue.toDouble()) {
            return false
        }

        if (maximalValue != null && number > maximalValue.toDouble()) {
            return false
        }

        return true
    }

    override fun bind(binding: ViewBinding) {
        val numericBinding = binding as ItemNumericFieldEntryBinding

        numericBinding.textInputLayout.hint = label
        numericBinding.editText.setText(value.toString())
    }

    private fun getErrorMessage(value: String?, resources: Resources): String? {
        if (value == null) {
            return resources.getString(
                cz.vvoleman.phr.common_datasource.R.string.field_error_empty
            )
        }

        val validity = isValid(value)

        if (validity) {
            return null
        }

        // If minimalValue and maximalValue is set, return message about range
        if (minimalValue != null && maximalValue != null) {
            return resources.getString(
                R.string.field_error_value_range,
                minimalValue,
                maximalValue
            )
        }

        // If only minimalValue is set, return message about minimalValue
        if (minimalValue != null) {
            return resources.getString(
                R.string.field_error_value_minimal,
                minimalValue
            )
        }

        // If only maximalValue is set, return message about maximalValue
        if (maximalValue != null) {
            return resources.getString(
                R.string.field_error_value_maximal,
                maximalValue
            )
        }

        // Unknown error
        return resources.getString(
            R.string.field_error_unknown
        )
    }
}
