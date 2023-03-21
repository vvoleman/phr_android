package cz.vvoleman.phr.common.ui.view.datepicker

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import cz.vvoleman.phr.common_datasource.databinding.DatePickerLayoutBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val dialog: DatePickerDialog
    private var editText: TextInputEditText
    private var listener: DatePickerListener? = null

    init {
        val layoutBinding =
            DatePickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        editText = layoutBinding.datePicker
        editText.showSoftInputOnFocus = false

        // Create DatePickerDialog
        dialog = DatePickerDialog(context)

        dialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month+1, dayOfMonth)
            Log.d(TAG, "Date change registered: $date")
            Log.d(TAG, "Date set: $date")
            setEditTextValue(date)
            editText.clearFocus()
            listener?.onDateSelected(date)
        }

        dialog.setOnDismissListener {
            editText.clearFocus()
        }


        // On click show dialog
        Log.d("DatePicker", "has listeners?: ${editText.hasOnClickListeners()}")
        editText.setOnFocusChangeListener { _, hasFocus ->
            Log.d("DatePicker", "has focus?: $hasFocus")
            if (hasFocus) {
                dialog.show()
            }
        }
    }

    fun setValue(value: String) {
        Log.d(TAG, "Setting value: $value")
        editText.setText(value)

        // value to Date
        val date = LocalDate.parse(value)
        dialog.updateDate(date.year, date.monthValue - 1, date.dayOfMonth)
    }

    fun getValue(): String {
        return editText.text.toString()
    }

    fun setListener(listener: DatePickerListener) {
        this.listener = listener
    }

    fun setDate(date: LocalDate) {
        Log.d(TAG, "Setting date setDate: $date")
        setEditTextValue(date)
        dialog.updateDate(date.year, date.monthValue, date.dayOfMonth)
    }

    private fun setEditTextValue(date: LocalDate) {
        editText.setText(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
    }

    interface DatePickerListener {
        fun onDateSelected(date: LocalDate)
    }

    companion object {
        private val TAG = "DatePicker"
    }

}