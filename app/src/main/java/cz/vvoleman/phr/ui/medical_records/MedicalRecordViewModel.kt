package cz.vvoleman.phr.ui.medical_records

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import cz.vvoleman.phr.data.repository.DiagnoseRepository
import cz.vvoleman.phr.ui.ADD_OK
import cz.vvoleman.phr.ui.EDIT_OK
import cz.vvoleman.phr.ui.shared.PatientSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MedicalRecordViewModel @Inject constructor(
    private val medicalRecordDao: MedicalRecordDao,
    private val diagnoseRepository: DiagnoseRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val medicalRecordsEventChannel = Channel<MedicalRecordsEvent>()

    private val preferencesFlow = preferencesManager.preferencesFlow

    val medicalRecordsEvent = medicalRecordsEventChannel.receiveAsFlow()

    val patientId = preferencesFlow.map { userPreferences ->
        userPreferences.patientId
    }
    val medicalRecords = patientId.flatMapLatest { patientId ->
        medicalRecordDao.getMedicalRecordByPatient(patientId)
    }.asLiveData()

    // Deletes record
    fun onRecordDeleteClick(medicalRecord: MedicalRecordWithDetails) = viewModelScope.launch {
        medicalRecordDao.deleteMedicalRecord(medicalRecord.medicalRecord)
        medicalRecordsEventChannel.send(
            MedicalRecordsEvent.ShowUndoDeleteRecordMessage(
                medicalRecord
            )
        )
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_OK -> showSavedConfirmation("Zpráva přidána")
            EDIT_OK -> showSavedConfirmation("Zpráva uložena")
        }
    }

    fun onUndoDeleteRecord(medicalRecord: MedicalRecordWithDetails) = viewModelScope.launch {
        medicalRecordDao.insertMedicalRecord(medicalRecord.medicalRecord)
    }

    private fun showSavedConfirmation(text: String) = viewModelScope.launch {
        medicalRecordsEventChannel.send(
            MedicalRecordsEvent.ShowSavedConfirmationMessage(text)
        )
    }

    sealed class MedicalRecordsEvent {
        data class ShowUndoDeleteRecordMessage(val medicalRecord: MedicalRecordWithDetails) :
            MedicalRecordsEvent()
        data class ShowSavedConfirmationMessage(val msg: String) : MedicalRecordsEvent()
        data class NetworkError(val msg: String) : MedicalRecordsEvent()
    }

}