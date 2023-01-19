package cz.vvoleman.phr.data.patient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patient WHERE id = :id")
    fun getPatientById(id: Int): Flow<Patient>

    @Query("SELECT * FROM patient WHERE name LIKE '%'||:name||'%'")
    fun getPatientByName(name: String): Flow<List<Patient>>

    @Insert
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

}