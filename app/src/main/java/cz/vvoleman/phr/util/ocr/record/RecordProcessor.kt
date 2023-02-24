package cz.vvoleman.phr.util.ocr.record

import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.record.field.DateFieldProcessor
import cz.vvoleman.phr.util.ocr.record.field.DiagnoseFieldProcessor

class RecordProcessor {
    private val TAG = "RecordProcessor"

    private val fieldProcessors = mapOf(
        "visitDate" to DateFieldProcessor(),
        "diagnose" to DiagnoseFieldProcessor(),
    )

    fun process(rawText: Text): RecognizedOptions {
        return RecognizedOptions(
            visitDate = fieldProcessors["visitDate"]?.process(rawText) ?: listOf(),
            diagnose = fieldProcessors["diagnose"]?.process(rawText) ?: listOf(),
            doctor = listOf("doctor"),
            patient = listOf("patient"),
        )
    }
}