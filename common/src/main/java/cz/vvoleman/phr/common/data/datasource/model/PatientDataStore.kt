package cz.vvoleman.phr.common.data.datasource.model

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(DATA_STORE_NAME)

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            Preferences(
                patientId = preferences[PreferencesKeys.PATIENT_ID] ?: 1
            )
        }

    suspend fun updatePatient(patientId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PATIENT_ID] = patientId
        }
    }

    private object PreferencesKeys {
        val PATIENT_ID = preferencesKey<Int>("patient_id")
    }

    companion object {
        private const val TAG = "PatientDataStore"
        private const val DATA_STORE_NAME = "patient_data_store"
    }

    data class Preferences(
        val patientId: Int
    )

}