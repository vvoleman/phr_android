package cz.vvoleman.phr.common.ui.component.picker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ViewTimePickerBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val dialogBuilder: MaterialTimePicker.Builder
    private var editText: TextInputEditText
    private var listener: TimePickerListener? = null
    private var time: LocalTime? = null
    private var dialogTitle: String? = null

    init {
        val layoutBinding =
            ViewTimePickerBinding.inflate(LayoutInflater.from(context), this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Picker,
            0, 0
        ).apply {

            try {
                layoutBinding.timePicker.hint = getString(R.styleable.Picker_hint)
                layoutBinding.textInputLayoutTimePicker.endIconMode =
                    if (getBoolean(R.styleable.Picker_showEndIcon, true)) {
                        TextInputLayout.END_ICON_CUSTOM
                    } else {
                        TextInputLayout.END_ICON_NONE
                    }
                dialogTitle = getString(R.styleable.Picker_dialogTitle)
            } finally {
                recycle()
            }
        }

        editText = layoutBinding.timePicker
        editText.showSoftInputOnFocus = false

        val fallbackTime = LocalTime.now()

        // Create DatePickerDialog
        dialogBuilder = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText(dialogTitle)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(fallbackTime.hour)
            .setMinute(fallbackTime.minute)

        // On click show dialog
        editText.setOnFocusChangeListener { _, hasFocus ->
            Log.d(TAG, "has focus?: $hasFocus")
            if (hasFocus) {
                listener?.injectFragmentManager()?.let { fragmentManager ->
                    showDialog(fragmentManager)
                }
            }
        }
    }

    private fun showDialog(fragmentManager: FragmentManager) {
        val dialog = dialogBuilder.build()

        dialog.addOnPositiveButtonClickListener {
            val time = LocalTime.of(dialog.hour, dialog.minute)
            setEditTextValue(time)
            editText.clearFocus()
            listener?.onTimeSelected(time)
        }


        dialog.addOnDismissListener {
            editText.clearFocus()
        }

        dialog.show(fragmentManager, "timePicker")
    }

    fun setValue(value: String) {
        editText.setText(value)

        // value to Date
        val time = LocalTime.parse(value)
        dialogBuilder
            .setHour(time.hour)
            .setMinute(time.minute)
    }

    fun getValue(): String {
        return editText.text.toString()
    }

    fun setListener(listener: TimePickerListener) {
        this.listener = listener
    }

    fun setError(error: String?) {
        val layout = editText.parent.parent as TextInputLayout
        layout.error = error
    }

    fun setTime(time: LocalTime) {
        setEditTextValue(time)
        dialogBuilder
            .setHour(time.hour)
            .setMinute(time.minute)
    }

    private fun setEditTextValue(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        editText.setText(time.format(formatter))
    }

    interface TimePickerListener : PickerListener {
        fun onTimeSelected(time: LocalTime)
    }

    companion object {
        private const val TAG = "TimePicker"
    }
}
