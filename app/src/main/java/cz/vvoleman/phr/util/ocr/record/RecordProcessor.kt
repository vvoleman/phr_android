package cz.vvoleman.phr.util.ocr.record

import android.util.Log
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.record.field.DiagnoseFieldProcessor
import cz.vvoleman.phr.util.ocr.record.field.PatientFieldProcessor
import cz.vvoleman.phr.util.ocr.text_processor.DateFieldProcessor
import javax.inject.Inject

class RecordProcessor @Inject constructor(
    private val patientFieldProcessor: PatientFieldProcessor
) {
    private val TAG = "RecordProcessor"

    suspend fun process(rawText: Text): RecognizedOptions {
        Log.d(TAG, rawText.text)
        val options = RecognizedOptions()
        options.visitDate = DateFieldProcessor().process(rawText)
        options.diagnose = DiagnoseFieldProcessor().process(rawText)

        options.patient = patientFieldProcessor.let {
            it.setOptions(options)
            it.process(rawText)
        }

        return options
    }
}
