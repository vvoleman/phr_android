package cz.vvoleman.phr.common.data.datasource.model

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
    fun getAllPatients(): Flow<List<PatientDataSourceModel>>

    @Query("SELECT * FROM patient WHERE id = :id")
    fun getPatientById(id: Int): Flow<PatientDataSourceModel>

    @Query("SELECT * FROM patient WHERE name LIKE '%'||:name||'%'")
    fun getPatientByName(name: String): Flow<List<PatientDataSourceModel>>

    @Query("SELECT * FROM patient WHERE birth_date = :date")
    fun getPatientByBirthDate(date: LocalDate): Flow<List<PatientDataSourceModel>>

    @Insert
    suspend fun insertPatient(patient: PatientDataSourceModel)

    @Update
    suspend fun updatePatient(patient: PatientDataSourceModel)

    @Delete
    suspend fun deletePatient(patient: PatientDataSourceModel)

}