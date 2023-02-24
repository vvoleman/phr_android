package cz.vvoleman.phr.util.ocr.text_processor

import com.google.mlkit.vision.text.Text

interface FieldProcessor {

    fun process(text: Text): List<String>

}