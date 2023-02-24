package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.util.ocr.record.RecordProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecognizerViewModel : ViewModel() {

    private val TAG = "RecognizerViewModel"

    private val recordProcessor: RecordProcessor = RecordProcessor()

    private val rawText: MutableStateFlow<Text?> = MutableStateFlow(null)
    val options = rawText.map{ text ->
        text?.let {
            recordProcessor.process(it)
        }
    }

    fun setRawText(text: Text) {
        rawText.value = text
    }

}

