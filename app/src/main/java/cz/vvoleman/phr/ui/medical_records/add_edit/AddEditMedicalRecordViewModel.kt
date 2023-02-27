package cz.vvoleman.phr.ui.medical_records.add_edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.core.Diagnose
import cz.vvoleman.phr.data.core.DiagnoseWithGroup
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.repository.DiagnoseRepository
import cz.vvoleman.phr.data.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.ui.ADD_OK
import cz.vvoleman.phr.ui.EDIT_OK
import cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.RecognizerViewModel
import cz.vvoleman.phr.util.getByPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AddEditMedicalRecordViewModel @Inject constructor(
    private val medicalRecordDao: MedicalRecordDao,
    private val diagnoseDao: DiagnoseDao,
    private val diagnoseGroupDao: DiagnoseGroupDao,
    private val state: SavedStateHandle,
    private val preferencesManager: PreferencesManager,
    private val diagnoseRepository: DiagnoseRepository
) : ViewModel() {

    private val TAG = "AddEditMedicalRecordViewModel"

    private val patientId = preferencesManager.preferencesFlow.map { it.patientId }

    private val medicalRecord = state.get<MedicalRecord>(MEDICAL_RECORD)

    var recordDate =
        state.getStateFlow<LocalDate>(DATE, medicalRecord?.date ?: LocalDate.now())

    var recordText = state.get<String>(TEXT) ?: medicalRecord?.text ?: ""
        set(value) {
            field = value
            state[TEXT] = value
        }

    var patient = state.get<Patient>(PATIENT)
        set(value) {
            field = value
            state[PATIENT] = value
        }

    val diagnoseSearchQuery = MutableStateFlow("")
    val allDiagnoses = diagnoseSearchQuery.flatMapLatest { query ->
        Log.d(TAG, "allDiagnoses: $query")
        diagnoseRepository.getDiagnoses(query).flow.cachedIn(viewModelScope)
    }.catch {
        medicalRecordsEventChannel.send(MedicalRecordEvent.NetworkError("Nelze načíst diagnózy ze serveru"))
    }.asLiveData()

    val selectedDiagnose = MutableStateFlow<cz.vvoleman.phr.data.core.DiagnoseWithGroup?>(null)

    private val medicalRecordsEventChannel = Channel<MedicalRecordEvent>()
    val medicalRecordsEvent = medicalRecordsEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (medicalRecord != null) {
                setDate(medicalRecord.date)
                recordText = medicalRecord.text
                selectedDiagnose.value =
                    diagnoseDao.getById(medicalRecord.diagnoseId).first().toDiagnoseWithGroup()
            }
        }
    }

    suspend fun onSaveClick() {
        if (recordText.isBlank()) {
            showInvalidInputMessage("Text zprávy musí být vyplněn")
            return
        }
        if (recordDate == null) {
            showInvalidInputMessage("Datum musí být vyplněno")
            return
        }
        if (selectedDiagnose.value == null) {
            showInvalidInputMessage("Nebyla vybrána žádná diagnóza")
            return
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = recordDate
//        var date: LocalDate? = null
//        try {
//            date = LocalDate.parse(recordDate)
//        } catch (e: Exception) {
//            showInvalidInputMessage("Neplatné datum")
//            return
//        }

        val diagnose = selectedDiagnose.value!!
        diagnoseRepository.create(diagnose)

        val patientId = patient?.id ?: patientId.first()

        if (medicalRecord != null) {
            val updatedRecord = medicalRecord.copy(
                text = recordText,
                date = date.value,
                diagnoseId = diagnose.diagnose.id
            )
            updateRecord(updatedRecord)
        } else {
            Log.d(TAG, "onSaveClick patient ID: $patientId")
            val newRecord = MedicalRecord(
                text = recordText,
                date = date.value,
                facilityId = 1,
                patientId = patientId,
                diagnoseId = diagnose.diagnose.id
            )
            Log.d(TAG, "onSaveClick: $newRecord")
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

    fun setRecognizedOptions(options: RecognizerViewModel.SelectedOptions) =
        viewModelScope.launch {
            //1. Get diagnose object (local or remote)
            //2. If patient is not current profile, show snackbar with info. Allow to undo this
            //3. Set values
            options.diagnose?.let {
                diagnoseRepository.getDiagnoseById(it)
            }?.let { selectedDiagnose.value = it.toDiagnoseWithGroup() }

            options.visitDate?.let {
                setDate(it)
            }

            options.patient?.let {
                patient = options.patient
                if (it.id != patient?.id) {
                    medicalRecordsEventChannel.send(MedicalRecordEvent.DifferentPatientSelected(it))
                }
            }
        }

    fun setDate(date: LocalDate) {
        state[DATE] = date
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
        private const val PATIENT = "patient"
    }

    sealed class MedicalRecordEvent {
        data class ShowInvalidInputMessage(val msg: String) : MedicalRecordEvent()
        data class NavigateBackWithResult(val result: Int) : MedicalRecordEvent()
        data class NetworkError(val msg: String) : MedicalRecordEvent()
        data class DifferentPatientSelected(val differentPatient: Patient) : MedicalRecordEvent()
    }

}