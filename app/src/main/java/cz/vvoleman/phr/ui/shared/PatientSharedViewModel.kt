package cz.vvoleman.phr.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.patient.PatientDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class PatientSharedViewModel @Inject constructor(
    private val patientDao: PatientDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _preferencesFlow = preferencesManager.preferencesFlow

    private val _selectedFlow = _preferencesFlow.map { userPreferences ->
        userPreferences.patientId
    }.flatMapLatest {id ->
        patientDao.getPatientById(id)
    }

    val selectedPatient = _selectedFlow

    fun updatePatient(patientId: Int) = viewModelScope.launch {
        preferencesManager.updatePatient(patientId)
    }
}