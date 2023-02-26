package cz.vvoleman.phr.ui.views.date_picker

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import cz.vvoleman.phr.databinding.DatePickerLayoutBinding
import cz.vvoleman.phr.util.getCurrentDay
import cz.vvoleman.phr.util.getCurrentMonth
import cz.vvoleman.phr.util.getCurrentYear
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

private val TAG = "DatePicker"

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var dialog: DatePickerDialog
    private var editText: EditText
    private var listener: DatePickerListener? = null

    init {
        val layoutBinding =
            DatePickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        editText = layoutBinding.datePicker
        editText.showSoftInputOnFocus = false

        // Create DatePickerDialog
        dialog = DatePickerDialog(context)

        dialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month+1, dayOfMonth)

            Log.d(TAG, "Date set: $date")
            editText.setText(date.toString())
            editText.clearFocus()
            listener?.onDateSelected(date)
        }

        dialog.setOnDismissListener {
            editText.clearFocus()
        }


        // On click show dialog
        Log.d("DatePicker", "has listeners?: ${editText.hasOnClickListeners()}")
        editText.setOnFocusChangeListener { v, hasFocus ->
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
        editText.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        dialog.updateDate(date.year, date.monthValue, date.dayOfMonth)
    }

    interface DatePickerListener {
        fun onDateSelected(date: LocalDate)
    }


}