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
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel @Inject constructor(
    private val patientDao: PatientDao,
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val allPatients = searchQuery.flatMapLatest { searchQuery ->
        patientDao.getPatientByName(searchQuery)
    }.asLiveData()

}