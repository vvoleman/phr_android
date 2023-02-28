package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.util.ocr.record.RecordProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecognizerViewModel @Inject constructor(
    private val recordProcessor: RecordProcessor,
    private val state: SavedStateHandle
) : ViewModel() {

    private val TAG = "RecognizerViewModel"

    private val recognizerEventChannel = Channel<RecognizerEvent>()
    val recognizerEvent = recognizerEventChannel.receiveAsFlow()

    private val rawText: MutableStateFlow<Text?> = MutableStateFlow(null)
    val options = rawText.map{ text ->
        text?.let {
            recordProcessor.process(it)
        }
    }

    val selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedPatient = MutableStateFlow<Patient?>(null)
    val selectedDiagnose = MutableStateFlow<String?>(null)

    fun setRawText(text: Text) {
        rawText.value = text
    }

    init {
        viewModelScope.launch {
            options.collect {
                if (it != null) {
                    selectedDate.value = it.visitDate.firstOrNull()?.value
                    selectedPatient.value = it.patient.firstOrNull()?.value
                    selectedDiagnose.value = it.diagnose.firstOrNull()?.value
                }
            }
        }
    }

    fun getSelectedOptions(): SelectedOptions {
        return SelectedOptions(
            selectedDate.value,
            selectedPatient.value,
            selectedDiagnose.value
        )
    }

    sealed class RecognizerEvent {
    }

    @Parcelize
    data class SelectedOptions(
        val visitDate: LocalDate?,
        val patient: Patient?,
        val diagnose: String?
    ) : Parcelable {}

}

