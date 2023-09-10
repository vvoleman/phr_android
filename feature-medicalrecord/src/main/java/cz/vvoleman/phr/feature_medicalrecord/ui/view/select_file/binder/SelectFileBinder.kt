package cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.binder

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.R
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.OptionAdapter
import cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.OptionItem
import kotlinx.coroutines.CoroutineScope
import java.time.format.DateTimeFormatter
import java.util.Locale

class SelectFileBinder :
    BaseViewStateBinder<SelectFileViewState, FragmentSelectFileBinding, SelectFileBinder.Notification>() {

    private lateinit var selectOptionsDialog: Dialog

    override fun bind(viewBinding: FragmentSelectFileBinding, viewState: SelectFileViewState) {
        viewBinding.progress.visibility = getVisibility(viewState.isLoading())

        if (viewState.previewUri != null) {
            viewBinding.preview.setImageBitmap(viewState.previewUri)
        }

        if (viewState.recognizedOptions != null) {
            setDialog(viewState)
        }

        viewBinding.confirmButton.visibility = getVisibility(viewState.recognizedOptions != null)
    }

    override fun init(
        viewBinding: FragmentSelectFileBinding,
        context: Context,
        lifecycleScope: CoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        selectOptionsDialog = Dialog(context).apply { setContentView(R.layout.dialog_select_options) }
    }

    fun openDialog() {
        selectOptionsDialog.show()
    }

    @Suppress("LongMethod")
    private fun setDialog(viewState: SelectFileViewState) {
        if (viewState.recognizedOptions == null) {
            return
        }

        selectOptionsDialog.apply {
            // set value to text view
            findViewById<Spinner>(R.id.diagnose_spinner).apply {
                val items = viewState.recognizedOptions.diagnose.map { OptionItem(it.id, it.name) }
                adapter = OptionAdapter(
                    fragmentContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    items
                )
            }

            findViewById<Spinner>(R.id.date_spinner).apply {
                val items = viewState.recognizedOptions.visitDate.map {
                    val formatter =
                        DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
                            .withLocale(Locale.getDefault())
                    OptionItem(it.toString(), it.format(formatter))
                }
                adapter = OptionAdapter(
                    fragmentContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    items
                )
            }

            val hasPatients = viewState.recognizedOptions.patient.size > 1
            findViewById<LinearLayout>(R.id.patient_layout).apply {
                this.visibility = if (hasPatients) View.VISIBLE else View.GONE
            }

            if (hasPatients) {
                findViewById<Spinner>(R.id.patient_spinner).apply {
                    val items =
                        viewState.recognizedOptions.patient.map { OptionItem(it.id, it.name) }
                    adapter = OptionAdapter(
                        fragmentContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        items
                    )
                }
            }

            findViewById<Button>(R.id.confirm_button).setOnClickListener {
                val diagnose = (findViewById<Spinner>(R.id.diagnose_spinner).selectedItem as OptionItem?)?.value
                val visitDate = (findViewById<Spinner>(R.id.date_spinner).selectedItem as OptionItem?)?.value
                val patient = if (hasPatients) {
                    (findViewById<Spinner>(R.id.patient_spinner).selectedItem as OptionItem?)?.value
                } else {
                    null
                }

                this.dismiss()
                notify(Notification.ConfirmWithOptions(diagnose, visitDate, patient))
            }

            findViewById<Button>(R.id.confirm_just_file_button).setOnClickListener {
                this.dismiss()
                notify(Notification.ConfirmWithoutOptions)
            }

            findViewById<Button>(R.id.cancel_button).setOnClickListener {
                this.dismiss()
            }
        }
    }

    private fun getVisibility(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        const val TAG = "SelectFileBinder"
    }

    sealed class Notification {
        data class ConfirmWithOptions(val diagnose: String?, val visitDate: String?, val patient: String?) :
            Notification()
        object ConfirmWithoutOptions : Notification()
    }
}
