package cz.vvoleman.phr.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

data class UserPreferences(val patientId: Int)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_preferences")

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
            val patientId = preferences[PreferencesKeys.PATIENT_ID] ?: 1
            UserPreferences(patientId)
        }

    suspend fun updatePatient(patientId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PATIENT_ID] = patientId
        }
    }

    private object PreferencesKeys {
        val PATIENT_ID = preferencesKey<Int>("patient_id")
    }

}