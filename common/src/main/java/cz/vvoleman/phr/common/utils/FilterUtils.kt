package cz.vvoleman.phr.common.utils

import android.text.InputFilter
import android.text.Spanned

class NumericMinFilter(
    private val min: Number,
) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val input = (
            dest.subSequence(0, dstart).toString() + source + dest.subSequence(
                dend,
                dest.length
            )
            ).toDoubleOrNull()

        if (input != null && input < min.toDouble()) {
            return ""
        }

        return null
    }
}
