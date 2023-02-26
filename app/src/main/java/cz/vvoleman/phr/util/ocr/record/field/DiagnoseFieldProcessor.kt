package cz.vvoleman.phr.util.ocr.record.field

import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.text_processor.FieldProcessor
import cz.vvoleman.phr.util.ocr.text_processor.field.result.TextFieldResult

class DiagnoseFieldProcessor : FieldProcessor<TextFieldResult> {

    private val regexp = "[A-TV-Z][0-9][0-9AB]\\.?[0-9A-TV-Z]{0,4}"

    override suspend fun process(text: Text): List<TextFieldResult> {
        val textValue = text.text

        val matches = regexp.toRegex().findAll(textValue)

        val codes = mutableListOf<TextFieldResult>()
        for (match in matches) {
            codes.add(TextFieldResult(match.value))
        }

        return codes
    }

}