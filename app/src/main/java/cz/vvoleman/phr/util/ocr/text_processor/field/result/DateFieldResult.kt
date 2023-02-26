package cz.vvoleman.phr.util.ocr.text_processor.field.result

import android.os.Parcelable
import cz.vvoleman.phr.util.ocr.text_processor.FieldResult
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class DateFieldResult(
    override val value: LocalDate
) : FieldResult<LocalDate>(), Parcelable {

    override fun toString(): String {
        return value.toString()
    }
}
