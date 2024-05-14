package cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.binder

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.DialogSelectOptionsBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.RecognizedOptionsPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectFileViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.adapter.OptionAdapter
import cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.adapter.OptionItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class SelectFileBinder :
    BaseViewStateBinder<SelectFileViewState, FragmentSelectFileBinding, SelectFileBinder.Notification>() {

    private lateinit var selectOptionsDialog: AlertDialog
    private lateinit var selectOptionsBinding: DialogSelectOptionsBinding
    private var lastRecognizedOptions: RecognizedOptionsPresentationModel? = null

    override fun bind(viewBinding: FragmentSelectFileBinding, viewState: SelectFileViewState) {
        lastRecognizedOptions = viewState.recognizedOptions
        viewBinding.progress.visibility = getVisibility(viewState.isLoading())

        if (viewState.previewUri != null) {
            viewBinding.preview.setImageBitmap(viewState.previewUri)
        }

        if (viewState.recognizedOptions != null) {
            bindDialog(viewState)
        }

        viewBinding.confirmButton.visibility = getVisibility(
            viewState.recognizedOptions != null,
            hidden = View.INVISIBLE
        )
    }

    override fun init(
        viewBinding: FragmentSelectFileBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        selectOptionsBinding = DialogSelectOptionsBinding.inflate(LayoutInflater.from(context))

        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(selectOptionsBinding.root)
        builder.setCancelable(false)
        builder.setTitle(R.string.select_options_dialog_title)
        builder.setNegativeButton("Pouze soubor") { dialog, _ ->
            notify(Notification.ConfirmWithoutOptions)
            dialog.dismiss()
        }
        builder.setPositiveButton("Potvrdit") { dialog, _ ->
            val hasPatients = selectOptionsBinding.textInputLayoutPatient.visibility == View.VISIBLE
            val diagnose =
                lastRecognizedOptions
                    ?.diagnose
                    ?.firstOrNull { it.name == selectOptionsBinding.diagnoseSpinner.text.toString() }

            val formatter = DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault())
            val dateText = selectOptionsBinding.dateSpinner.text.toString()
            val date = if (dateText.isNotEmpty()) LocalDate.parse(dateText, formatter) else null

            notify(
                Notification.ConfirmWithOptions(
                    diagnose = diagnose?.id,
                    visitDate = date,
                    patient = if (hasPatients) selectOptionsBinding.patientSpinner.toString() else null
                )
            )
            dialog.dismiss()
        }
        builder.setNeutralButton("Zrušit") { dialog, _ ->
            dialog.dismiss()
        }

        selectOptionsDialog = builder.create()
    }

    fun openDialog() {
        selectOptionsDialog.show()
    }

    private fun bindDialog(viewState: SelectFileViewState) {
        if (viewState.recognizedOptions == null) {
            return
        }

        selectOptionsBinding.apply {
            val diagnoses = viewState.recognizedOptions.diagnose.map { OptionItem(it.id, it.name) }
            diagnoseSpinner.setAdapter(
                OptionAdapter(
                    fragmentContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    diagnoses
                )
            )

            if (viewState.recognizedOptions.diagnose.isNotEmpty()) {
                diagnoseSpinner.setText(diagnoses[0].toString(), false)
            }

            val dates = viewState.recognizedOptions.visitDate.map {
                val formatter = DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
                    .withLocale(Locale.getDefault())
                OptionItem(it.toString(), it.format(formatter))
            }
            dateSpinner.setAdapter(
                OptionAdapter(
                    fragmentContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    dates
                )
            )

            if (viewState.recognizedOptions.visitDate.isNotEmpty()) {
                Log.d(TAG, "bindDialog: ${viewState.recognizedOptions.visitDate}")
                dateSpinner.setText(dates[0].display, false)
            }
        }
    }

    override fun onDestroy(viewBinding: FragmentSelectFileBinding) {
        lastRecognizedOptions = null
        super.onDestroy(viewBinding)
    }

    companion object {
        const val TAG = "SelectFileBinder"
    }

    sealed class Notification {
        data class ConfirmWithOptions(val diagnose: String?, val visitDate: LocalDate?, val patient: String?) :
            Notification()

        object ConfirmWithoutOptions : Notification()
    }
}
