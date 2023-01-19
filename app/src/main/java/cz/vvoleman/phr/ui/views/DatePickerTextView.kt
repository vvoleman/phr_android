package cz.vvoleman.phr.ui.views

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import cz.vvoleman.phr.R

class DatePickerTextView (
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var textView: TextView
    private lateinit var datePicker: DatePicker

    init {
        // Inflate the layout file containing the TextView and DatePicker
        LayoutInflater.from(context).inflate(R.layout.date_picker_text_view, this, true)

        // Initialize the TextView and DatePicker
        textView = findViewById(R.id.textView_selectDate)
        datePicker = findViewById(R.id.datePicker)

        // Set an OnClickListener for the TextView
        textView.setOnClickListener {
            // Show the DatePicker in a dialog
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    // Do something with the selected date
                },
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            )
            datePickerDialog.show()
        }
    }
}