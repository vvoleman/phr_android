package cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields

import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.featureMeasurement.databinding.ItemTextFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldItem
import kotlinx.coroutines.CoroutineScope

class TextEntryFieldItem(
    private val label: String,
    private val value: String,
    id: String,
    coroutineScope: CoroutineScope,
    listener: EntryFieldItemListener,
) : EntryFieldItem(id, coroutineScope, listener) {

    override fun getType(): Type {
        return Type.TEXT
    }

    override fun isValid(value: String?): Boolean {
        return value != null
    }

    override fun initField(binding: ViewBinding) {
    }

    override fun bind(binding: ViewBinding) {
        val numericBinding = binding as ItemTextFieldEntryBinding

        numericBinding.textInputLayout.hint = label
        numericBinding.editText.setText(value.toString())
    }
}
