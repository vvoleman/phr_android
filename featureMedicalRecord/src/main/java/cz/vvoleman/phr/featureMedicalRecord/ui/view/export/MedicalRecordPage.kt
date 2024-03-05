package cz.vvoleman.phr.featureMedicalRecord.ui.view.export

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicalRecord.databinding.DocumentPageMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.export.ExportMedicalRecordParamsPresentationModel
import java.io.File
import java.time.LocalDateTime

class MedicalRecordPage(
    private val params: ExportMedicalRecordParamsPresentationModel,
    private val context: Context,
) : DocumentPage() {

    override fun bind(binding: ViewBinding) {
        val viewBinding = binding as DocumentPageMedicalRecordBinding
        val bitmap = InputImage.fromFilePath(context, Uri.fromFile(File(params.asset.url)))
        val details = getDetails()!!

        viewBinding.textViewDiagnose.text = params.medicalRecord.diagnose?.id ?: "Bez diagnózy"
        viewBinding.textViewMedicalWorkerName.text =
            params.medicalRecord.specificMedicalWorker?.medicalWorker?.name ?: "Bez lékaře"

        viewBinding.textViewCreatedAt.text = params.medicalRecord.visitDate.toLocalString()
        viewBinding.imageViewRecord.setImageBitmap(bitmap.bitmapInternal)
        viewBinding.textViewGeneratedAt.text = LocalDateTime.now().toLocalString()
        viewBinding.textViewPatientName.text = params.medicalRecord.patient.name
        viewBinding.textViewPage.text = "${details.currentPage}/${details.totalPages}"
    }

    override fun getViewBinding(inflater: LayoutInflater): ViewBinding {
        return DocumentPageMedicalRecordBinding.inflate(inflater)
    }
}
