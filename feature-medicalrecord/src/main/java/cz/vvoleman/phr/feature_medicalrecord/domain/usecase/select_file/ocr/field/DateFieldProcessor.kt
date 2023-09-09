package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.DateFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.FieldProcessor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFieldProcessor : FieldProcessor<DateFieldResultDomainModel> {

    private val regexps: Map<String, String> = mapOf(
        "dd.MM.yyyy" to "(\\d{2})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{4})",
        "d.MM.yyyy" to "(\\d{1})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{4})",
        "dd.M.yyyy" to "(\\d{2})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{4})",
        "d.M.yyyy" to "(\\d{1})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{4})",
        "yyyy-MM-dd" to "(\\d{4})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{2})",
        "yyyy-M-d" to "(\\d{4})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{1})",
        "yyyy-MM-d" to "(\\d{4})[.|-]\\s*?(\\d{2})[.|-]\\s*?(\\d{1})",
        "yyyy-M-dd" to "(\\d{4})[.|-]\\s*?(\\d{1})[.|-]\\s*?(\\d{2})"
    )

    override suspend fun process(text: TextDomainModel): List<DateFieldResultDomainModel> {
        val list = mutableListOf<DateFieldResultDomainModel>()

        val textValue = text.getText()
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
                list.add(DateFieldResultDomainModel(date))
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
