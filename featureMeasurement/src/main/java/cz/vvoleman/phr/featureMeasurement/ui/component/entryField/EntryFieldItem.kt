package cz.vvoleman.phr.featureMeasurement.ui.component.entryField

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.databinding.ItemNumericFieldEntryBinding
import cz.vvoleman.phr.featureMeasurement.databinding.ItemTextFieldEntryBinding
import kotlinx.coroutines.CoroutineScope

abstract class EntryFieldItem(
    val id: String,
    protected val coroutineScope: CoroutineScope,
    protected val listener: EntryFieldItemListener? = null
) {

    fun validate(value: String?): ItemStatus {
        if (!isValid(value)) {
            return ItemStatus.Invalid
        }

        return ItemStatus.Valid(value = value)
    }

    abstract fun getType(): Type

    protected abstract fun isValid(value: String?): Boolean

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

    interface EntryFieldItemListener {
        fun onValueChanged(id: String, value: String)
    }

    sealed class ItemStatus {
        object Invalid : ItemStatus()
        data class Valid(val value: String?) : ItemStatus()
    }

}
