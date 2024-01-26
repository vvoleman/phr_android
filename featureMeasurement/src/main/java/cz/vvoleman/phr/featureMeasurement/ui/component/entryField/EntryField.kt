package cz.vvoleman.phr.featureMeasurement.ui.component.entryField

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemNumericFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.databinding.ItemTextFieldEntryBinding

abstract class EntryField(protected val listener: EntryFieldListener? = null) {

    abstract fun getType(): Type
    abstract fun getLabel(): String

    abstract fun initField(binding: ViewBinding)

    abstract fun bind(binding: ViewBinding)

    enum class Type(@LayoutRes val layoutId: Int) {
        NUMERIC(R.layout.item_numeric_field_entry) {
            override fun getBinding(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
                return ItemNumericFieldEntryBinding.inflate(inflater, parent, false)
            }
        },
        TEXT(R.layout.item_text_field_entry) {
            override fun getBinding(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
                return ItemTextFieldEntryBinding.inflate(inflater, parent, false)
            }
        };

        abstract fun getBinding(inflater: LayoutInflater, parent: ViewGroup): ViewBinding
    }

    interface EntryFieldListener {
        fun onValueChanged(id: String, value: String)
    }

}
