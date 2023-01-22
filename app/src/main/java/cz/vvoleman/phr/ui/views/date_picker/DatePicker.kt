package cz.vvoleman.phr.ui.views.date_picker

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import cz.vvoleman.phr.databinding.DatePickerLayoutBinding

class DatePicker constructor(
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

        val builder = AlertDialog.Builder(context)
        builder.setView(layoutBinding.root)
        builder.setPositiveButton("OK") { dialog, _ ->
            val date = editText.text.toString()
            if (listener != null) {
                val result = listener?.onSelected(date)
                if (result != null && result) {
                    dialog.dismiss()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        dialog = builder.create()

        editText.setOnClickListener {
            dialog.show()
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
        fun onSelected(date: String): Boolean
    }

}