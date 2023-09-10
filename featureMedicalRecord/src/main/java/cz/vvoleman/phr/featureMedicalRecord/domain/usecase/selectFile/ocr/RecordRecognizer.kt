package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr

import android.util.Log
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.RecognizedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.TextDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.DateFieldProcessor
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.DiagnoseFieldProcessor
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.PatientFieldProcessor

class RecordRecognizer(
    private val patientFieldProcessor: PatientFieldProcessor,
    private val dateFieldProcessor: DateFieldProcessor,
    private val diagnoseFieldProcessor: DiagnoseFieldProcessor
) {

    suspend fun process(rawText: TextDomainModel): RecognizedOptionsDomainModel {
        val options = RecognizedOptionsDomainModel()
        options.visitDate = dateFieldProcessor.process(rawText)
        options.diagnose = diagnoseFieldProcessor.process(rawText)

        options.patient = patientFieldProcessor.let {
            it.setOptions(options)
            it.process(rawText)
        }
        Log.d(TAG, "Recognized options: $options")
        return options
    }

    companion object {
        private const val TAG = "RecordRecognizer"
    }
}
