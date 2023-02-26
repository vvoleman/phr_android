package cz.vvoleman.phr.util.ocr.record

import android.os.Parcelable
import cz.vvoleman.phr.util.ocr.record.field.result.PatientFieldResult
import cz.vvoleman.phr.util.ocr.text_processor.field.result.DateFieldResult
import cz.vvoleman.phr.util.ocr.text_processor.field.result.TextFieldResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizedOptions(
    var visitDate: List<DateFieldResult> = emptyList(),
    var diagnose: List<TextFieldResult> = emptyList(),
    var patient: List<PatientFieldResult> = emptyList(),
) : Parcelable {}
