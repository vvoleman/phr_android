package cz.vvoleman.phr.data.room.patient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient")
    fun getAllPatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patient WHERE id = :id")
    fun getPatientById(id: Int): Flow<PatientEntity>

    @Query("SELECT * FROM patient WHERE name LIKE '%'||:name||'%'")
    fun getPatientByName(name: String): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patient WHERE birth_date = :date")
    fun getPatientByBirthDate(date: LocalDate): Flow<List<PatientEntity>>

    @Insert
    suspend fun insertPatient(patient: PatientEntity)

    @Update
    suspend fun updatePatient(patient: PatientEntity)

    @Delete
    suspend fun deletePatient(patient: PatientEntity)

}