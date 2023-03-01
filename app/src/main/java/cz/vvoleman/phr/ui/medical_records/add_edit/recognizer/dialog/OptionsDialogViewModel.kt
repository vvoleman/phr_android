package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.RecognizerViewModel
import cz.vvoleman.phr.util.ocr.record.RecognizedOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class OptionsDialogViewModel : ViewModel() {

    val options: MutableStateFlow<RecognizedOptions?> = MutableStateFlow(null)

    val selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedPatient = MutableStateFlow<Patient?>(null)
    val selectedDiagnose = MutableStateFlow<String?>(null)

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

    fun selectDate(position: Int) {
        selectedDate.value = options.value?.visitDate?.get(position)?.value
    }

    fun selectPatient(position: Int) {
        selectedPatient.value = options.value?.patient?.get(position)?.value
    }

    fun selectDiagnose(position: Int) {
        selectedDiagnose.value = options.value?.diagnose?.get(position)?.value
    }

}