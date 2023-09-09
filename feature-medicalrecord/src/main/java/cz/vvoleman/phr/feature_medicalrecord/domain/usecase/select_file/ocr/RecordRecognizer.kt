package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.RecognizedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.DateFieldProcessor
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.DiagnoseFieldProcessor
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.PatientFieldProcessor

class RecordRecognizer(
    private val patientFieldProcessor: PatientFieldProcessor,
    private val dateFieldProcessor: DateFieldProcessor,
    private val diagnoseFieldProcessor: DiagnoseFieldProcessor
) {
    private val TAG = "RecordProcessor"

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
}
