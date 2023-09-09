package cz.vvoleman.phr.util.filter

import android.util.Log
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

private val TAG = "RecordsFilter"

class RecordsFilter(
    private val allRecordsFlow: Flow<List<MedicalRecordWithDetails>>,
    private val preferences: PreferencesManager
) {

    val sortBy = preferences.preferencesFlow.map { userPreferences ->
        userPreferences.orderRecordsBy
    }

    val selectedFacilities = MutableStateFlow<List<Int>>(emptyList())

    val diagnoses = allRecordsFlow
        .map { record ->
            record.groupBy {
                it.diagnose?.id
            }.map {
                it.value.first().diagnose
            }
        }.onEach {
            Log.d(TAG, "diagnoses: ${it.size}")
        }

    suspend fun changeSortBy(sortBy: String) {
        preferences.setOrderRecordsBy(sortBy)
    }

    suspend fun toggleFacility(facilityId: Int) {
        val facilities = selectedFacilities.value.toMutableList()
        if (facilities.contains(facilityId)) {
            facilities.remove(facilityId)
        } else {
            facilities.add(facilityId)
        }
        selectedFacilities.value = facilities
    }
}
