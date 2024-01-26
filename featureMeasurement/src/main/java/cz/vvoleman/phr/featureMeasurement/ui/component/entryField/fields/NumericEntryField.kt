package cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields

import android.util.Log
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.featureMeasurement.databinding.ItemNumericFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NumericEntryField(
    private val coroutineScope: CoroutineScope,
    private val label: String,
    private val value: Number,
    listener: EntryFieldListener,
) : EntryField(listener) {
    override fun getType(): Type {
        return Type.NUMERIC
    }
    override fun getLabel(): String {
        return label
    }

    override fun initField(binding: ViewBinding) {
        val numericBinding = binding as ItemNumericFieldEntryBinding

        Log.d("NumericEntryField", "initField: $label")
        numericBinding.editText.textChanges()
            .debounce(150)
            .onEach {
                val valid = isValid(it.toString())
                Log.d("NumericEntryField", "initField: $it $valid")
                numericBinding.textInputLayout.error = if (valid) null else "Číslo musí být větší než 15"

            }.launchIn(coroutineScope)
    }

    fun isValid(value: String): Boolean {
        val number = value.toDoubleOrNull()

        return number != null && number >= 15
    }

    override fun bind(binding: ViewBinding) {
        val numericBinding = binding as ItemNumericFieldEntryBinding

        numericBinding.textInputLayout.hint = label
        numericBinding.editText.setText(value.toString())
    }
}
