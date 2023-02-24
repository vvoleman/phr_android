package cz.vvoleman.phr.util.ocr.record.field

import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.text_processor.FieldProcessor

class DiagnoseFieldProcessor : FieldProcessor {

    private val regexp = "[A-TV-Z][0-9][0-9AB]\\.?[0-9A-TV-Z]{0,4}"

    override fun process(text: Text): List<String> {
        val textValue = text.text

        val matches = regexp.toRegex().findAll(textValue)

        val codes = mutableListOf<String>()
        for (match in matches) {
            codes.add(match.value);
        }

        return codes
    }

}