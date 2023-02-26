package cz.vvoleman.phr.util.ocr.text_processor

import android.util.Log
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.text_processor.field.result.DateFieldResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateFieldProcessor : FieldProcessor<DateFieldResult> {

    private val regexps: Map<String,String> = mapOf(
        "dd.MM.yyyy" to "(\\d{2})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{4})",
        "d.MM.yyyy" to "(\\d{1})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{4})",
        "dd.M.yyyy" to "(\\d{2})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{4})",
        "d.M.yyyy" to "(\\d{1})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{4})",
        "yyyy-MM-dd" to "(\\d{4})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{2})",
        "yyyy-M-d" to "(\\d{4})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{1})",
        "yyyy-MM-d" to "(\\d{4})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{1})",
        "yyyy-M-dd" to "(\\d{4})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{2})",
    )

    override suspend fun process(text: Text): List<DateFieldResult> {
        val list = mutableListOf<DateFieldResult>()

        val textValue = text.text
        Log.d(TAG, "textValue: $textValue")
        for (entry in regexps) {
            val format = entry.key
            val regexp = "\\b${entry.value}"

            val matchResult = regexp.toRegex().findAll(textValue)

            for (result in matchResult) {
                Log.d(TAG, "result: $result")

                // remove all whitespaces
                val cleanResult = result.value.replace(" ", "")
                val date = LocalDate.parse(cleanResult, DateTimeFormatter.ofPattern(format))
                list.add(DateFieldResult(date))
                /*try {
                    val date = LocalDate.parse(result.value, DateTimeFormatter.ofPattern(format))
                    list.add(DateFieldResult(date))

                } catch (e: DateTimeParseException) {
                    Log.d(TAG, "Exception: ${e.parsedString}")

                    continue
                }*/
            }
        }
        return list
    }

    companion object {
        private const val TAG = "DateFieldProcessor"
    }

}