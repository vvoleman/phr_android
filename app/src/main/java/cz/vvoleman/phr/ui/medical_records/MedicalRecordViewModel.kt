package cz.vvoleman.phr.ui.medical_records

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.OrderRecordsBy
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import cz.vvoleman.phr.data.repository.DiagnoseRepository
import cz.vvoleman.phr.ui.ADD_OK
import cz.vvoleman.phr.ui.EDIT_OK
import cz.vvoleman.phr.ui.shared.PatientSharedViewModel
import cz.vvoleman.phr.util.getByPattern
import cz.vvoleman.phr.util.getCurrentYear
import cz.vvoleman.phr.util.getNameOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDate
import javax.inject.Inject

private val TAG = "MedicalRecordViewModel"

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
    }

    val orderBy = preferencesFlow.map { userPreferences ->
        userPreferences.orderRecordsBy
    }

    val sections = combine(medicalRecords, orderBy) { medicalRecords, orderBy ->
        val sections = mutableListOf<Section>()
        when (orderBy) {
            OrderRecordsBy.BY_DATE -> {
                medicalRecords.groupBy { it.medicalRecord.date.getByPattern("yyyy-MM") }
                    .forEach { (date, records) ->
                        val parts = date.split("-")
                        val month = parts[1].toInt()
                        val year = parts[0].toInt()

                        // Get locale name of month
                        val convertedDate = Date.valueOf(LocalDate.of(year, month, 1).toString())
                        val name = "${convertedDate.getNameOfMonth()} ${convertedDate.getCurrentYear()}"
                        sections.add(Section(name, records))
                    }
            }
            OrderRecordsBy.BY_FACILITY -> {
                medicalRecords.groupBy { it.facility.name }
                    .forEach { (facility, records) ->
                        sections.add(Section(facility, records))
                    }
            }
        }
        sections
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