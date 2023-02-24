package cz.vvoleman.phr.util.ocr.record.field

import android.util.Log
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.text_processor.FieldProcessor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFieldProcessor : FieldProcessor {

    private val regexps: Map<String,String> = mapOf(
        "dd.MM.yyyy" to "(\\d{2})[.](\\d{2})[.](\\d{4})",
        "d.MM.yyyy" to "(\\d{1})[.](\\d{2})[.](\\d{4})",
        "dd.M.yyyy" to "(\\d{2})[.](\\d{1})[.](\\d{4})",
        "d.M.yyyy" to "(\\d{1})[.](\\d{1})[.](\\d{4})",
        "yyyy-MM-dd" to "(\\d{4})[-](\\d{1,2})[-](\\d{1,2})",
        "yyyy-M-d" to "(\\d{4})[-](\\d{1})[-](\\d{1})",
        "yyyy-MM-d" to "(\\d{4})[-](\\d{1,2})[-](\\d{1})",
        "yyyy-M-dd" to "(\\d{4})[-](\\d{1})[-](\\d{1,2})",
    )

    override fun process(text: Text): List<String> {
        val list = mutableListOf<String>()

        val textValue = text.text
        Log.d(TAG, "textValue: $textValue")
        for (entry in regexps) {
            val format = entry.key
            val regexp = "\\b${entry.value}\\b"

            val matchResult = regexp.toRegex().findAll(textValue)

            for (result in matchResult) {
                Log.d(TAG, "result: $result")

                val date = LocalDate.parse(result.value, DateTimeFormatter.ofPattern(format))
                list.add("${date.year}-${date.monthValue}-${date.dayOfMonth}")
            }
        }
        return list
    }

    companion object {
        private const val TAG = "DateFieldProcessor"
    }

}