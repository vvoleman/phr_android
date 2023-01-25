package cz.vvoleman.phr.util.filter

import android.util.Log
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import kotlinx.coroutines.flow.Flow
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

    val facilities = allRecordsFlow
        .map { record ->
            Log.d(TAG, "records: ${record.size}")
            record.groupBy {
                it.facility.id
            }.map {
                it.value.first().facility
            }
        }.onEach {
            Log.d(TAG, "facility: ${it.size}")
        }

    val diagnoses = allRecordsFlow
        .map { record ->
            record.groupBy {
                it.diagnose.id
            }.map {
                it.value.first().diagnose
            }
        }.onEach {
            Log.d(TAG, "diagnoses: ${it.size}")
        }




}