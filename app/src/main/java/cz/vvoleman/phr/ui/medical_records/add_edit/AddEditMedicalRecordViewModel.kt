package cz.vvoleman.phr.ui.medical_records.add_edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.repository.DiagnoseRepository
import cz.vvoleman.phr.ui.ADD_OK
import cz.vvoleman.phr.ui.EDIT_OK
import cz.vvoleman.phr.util.getByPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditMedicalRecordViewModel @Inject constructor(
    private val medicalRecordDao: MedicalRecordDao,
    private val state: SavedStateHandle,
    private val preferencesManager: PreferencesManager,
    private val diagnoseRepository: DiagnoseRepository
) : ViewModel() {

    val medicalRecord = state.get<MedicalRecord>(MEDICAL_RECORD)

    var recordDate =
        state.get<String>(DATE) ?: medicalRecord?.date?.getByPattern("yyyy-MM-dd") ?: ""
        set(value) {
            field = value
            state[DATE] = value
        }

    var recordText = state.get<String>(TEXT) ?: medicalRecord?.text ?: ""
        set(value) {
            field = value
            state[TEXT] = value
        }

    private val medicalRecordsEventChannel = Channel<MedicalRecordEvent>()
    val medicalRecordsEvent = medicalRecordsEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (recordText.isBlank()) {
            showInvalidInputMessage("Text can't be empty")
            return
        }
        if (recordDate.isBlank()) {
            showInvalidInputMessage("Date can't be empty")
            return
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        var date: Date? = null
        try {
            date = sdf.parse(recordDate)
        } catch (e: Exception) {
            showInvalidInputMessage("Neplatné datum")
            return
        }

        if (medicalRecord != null) {
            val updatedRecord = medicalRecord.copy(text = recordText, date = date)
            updateRecord(updatedRecord)
        } else {
            val newRecord = MedicalRecord(text = recordText, date = date, facilityId = 1, patientId = 1, diagnoseId = "A00")
            createRecord(newRecord)
        }
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        medicalRecordsEventChannel.send(
            MedicalRecordEvent.ShowInvalidInputMessage(text)
        )
    }

    private fun createRecord(record: MedicalRecord) = viewModelScope.launch {
        medicalRecordDao.insertMedicalRecord(record)
        medicalRecordsEventChannel.send(
            MedicalRecordEvent.NavigateBackWithResult(ADD_OK)
        )
    }

    private fun updateRecord(record: MedicalRecord) = viewModelScope.launch {
        medicalRecordDao.updateMedicalRecord(record)
        medicalRecordsEventChannel.send(
            MedicalRecordEvent.NavigateBackWithResult(EDIT_OK)
        )
    }

    companion object {
        private const val MEDICAL_RECORD = "medicalRecord"
        private const val DATE = "date"
        private const val TEXT = "text"
    }

    sealed class MedicalRecordEvent {
        data class ShowInvalidInputMessage(val msg: String) : MedicalRecordEvent()
        data class NavigateBackWithResult(val result: Int) : MedicalRecordEvent()
    }

}