package cz.vvoleman.phr.util.ocr.record.field.result

import android.os.Parcelable
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.util.ocr.text_processor.FieldResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientFieldResult(
    override val value: Patient,
) : FieldResult<Patient>(), Parcelable {

    override fun toString(): String {
        return value.toString()
    }
}