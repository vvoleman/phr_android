package cz.vvoleman.phr.common.ui.component.picker

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.DatePickerLayoutBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val dialog: DatePickerDialog
    private var editText: TextInputEditText
    private var listener: DatePickerListener? = null
    private var date: LocalDate? = null

    init {
        val layoutBinding =
            DatePickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Picker,
            0,
            0
        ).apply {

            try {
                layoutBinding.datePicker.hint = getString(R.styleable.Picker_hint)
                layoutBinding.textInputLayoutDatePicker.endIconMode =
                    if (getBoolean(R.styleable.Picker_showEndIcon, true)) {
                        TextInputLayout.END_ICON_CUSTOM
                    } else {
                        TextInputLayout.END_ICON_NONE
                    }
            } finally {
                recycle()
            }
        }

        editText = layoutBinding.datePicker
        editText.showSoftInputOnFocus = false

        // Create DatePickerDialog
        dialog = DatePickerDialog(context)

        dialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month + 1, dayOfMonth)
            setEditTextValue(date)
            editText.clearFocus()
            listener?.onDateSelected(date)
        }

        dialog.setOnDismissListener {
            editText.clearFocus()
        }

        // On click show dialog
        editText.setOnFocusChangeListener { _, hasFocus ->
            Log.d(TAG, "has focus?: $hasFocus")
            if (hasFocus) {
                dialog.show()
            }
        }
    }

    fun setValue(value: String) {
        editText.setText(value)

        // value to Date
        val date = LocalDate.parse(value)
        dialog.updateDate(date.year, date.monthValue - 1, date.dayOfMonth)
    }

    fun getValue(): String {
        return editText.text.toString()
    }

    fun getDate(): LocalDate? {
        if (editText.text == null || editText.text.toString().isEmpty()) {
            return null
        }

        val datePicker = dialog.datePicker
        return LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
    }

    fun setListener(listener: DatePickerListener) {
        this.listener = listener
    }

    fun setDate(date: LocalDate) {
        setEditTextValue(date)
        dialog.updateDate(date.year, date.monthValue - 1, date.dayOfMonth)
    }

    fun setError(error: String?) {
        val layout = editText.parent.parent as TextInputLayout
        layout.error = error
    }

    private fun setEditTextValue(date: LocalDate) {
        editText.setText(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
    }

    interface DatePickerListener : PickerListener {
        fun onDateSelected(date: LocalDate)
    }

    companion object {
        private const val TAG = "DatePicker"
    }
}
