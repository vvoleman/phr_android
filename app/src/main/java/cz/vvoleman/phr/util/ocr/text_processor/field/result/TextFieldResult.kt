package cz.vvoleman.phr.util.ocr.text_processor.field.result

import android.os.Parcelable
import cz.vvoleman.phr.util.ocr.text_processor.FieldResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextFieldResult(
    override val value: String
) : FieldResult<String>(), Parcelable {

    override fun toString(): String {
        return value
    }
}
