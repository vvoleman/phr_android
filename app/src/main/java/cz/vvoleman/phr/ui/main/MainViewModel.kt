package cz.vvoleman.phr.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.UserPreferences
import cz.vvoleman.phr.data.patient.PatientDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val patientDao: PatientDao,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val preferencesFlow = preferencesManager.preferencesFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val patient = preferencesFlow.map { userPreferences ->
        userPreferences.patientId
    }.flatMapLatest {id ->
        patientDao.getPatientById(id)
    }.asLiveData()

    val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val allPatients = searchQuery.flatMapLatest { searchQuery ->
        patientDao.getPatientByName(searchQuery)
    }.asLiveData()

    fun updatePatient(patientId: Int) = viewModelScope.launch {
        preferencesManager.updatePatient(patientId)
    }

}