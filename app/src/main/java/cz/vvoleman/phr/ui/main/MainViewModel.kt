package cz.vvoleman.phr.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.data.room.patient.PatientDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel @Inject constructor(
    private val patientDao: PatientDao,
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val allPatients = searchQuery.flatMapLatest { searchQuery ->
        patientDao.getPatientByName(searchQuery)
    }.map {
        val list = mutableListOf<Patient>()
        for (patient in it) {
            list.add(patient.toPatient())
        }
        list.toList()
    }.asLiveData()

}