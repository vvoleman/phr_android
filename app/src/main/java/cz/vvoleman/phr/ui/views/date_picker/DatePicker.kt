package cz.vvoleman.phr.ui.views.date_picker

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.DatePickerDialogBinding
import cz.vvoleman.phr.databinding.DatePickerLayoutBinding

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var dialog: AlertDialog
    private var editText: EditText
    private var listener: DatePickerListener? = null

    init {
        val layoutBinding =
            DatePickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        editText = layoutBinding.datePicker

        // Binding for the dialog
        val binding = DatePickerDialogBinding.inflate(LayoutInflater.from(context), null, false)

        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.date_picker_dialog)
        builder.setPositiveButton("OK") { dialog, _ ->
            val picker = binding.datePicker
            val date = "${picker.year}-${picker.month+1}-${picker.dayOfMonth}"
            Log.d("DatePicker", "Date: $date")
            if (listener != null) {
                val result = listener?.onDateSelected(date)
                if (result != null && result) {
                    dialog.dismiss()
                    editText.clearFocus()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            editText.clearFocus()
        }
        dialog = builder.create()

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
        editText.setText(value)
    }

    fun getValue(): String {
        return editText.text.toString()
    }

    fun setListener(listener: DatePickerListener) {
        this.listener = listener
    }

    interface DatePickerListener {
        fun onDateSelected(date: String): Boolean
    }

}