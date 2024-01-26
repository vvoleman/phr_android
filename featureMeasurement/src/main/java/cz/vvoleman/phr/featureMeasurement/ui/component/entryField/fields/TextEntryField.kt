package cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields

import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.featureMeasurement.databinding.ItemTextFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryField

class TextEntryField(
    private val label: String,
    private val value: String
) : EntryField() {

    override fun getType(): Type {
        return Type.TEXT
    }

    override fun getLabel(): String {
        return label
    }

    override fun initField(binding: ViewBinding) {
    }

    override fun bind(binding: ViewBinding) {
        val numericBinding = binding as ItemTextFieldEntryBinding

        numericBinding.textInputLayout.hint = label
        numericBinding.editText.setText(value.toString())
    }
}
