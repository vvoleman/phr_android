package cz.vvoleman.phr.common.data.datasource.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient")
    fun getAll(): Flow<List<PatientDataSourceModel>>

    @Query("SELECT * FROM patient WHERE id = :id")
    fun getById(id: String): Flow<PatientDataSourceModel>

    @Query("SELECT * FROM patient WHERE name LIKE '%'||:name||'%'")
    fun getByName(name: String): Flow<List<PatientDataSourceModel>>

    @Query("SELECT * FROM patient WHERE birth_date = :date")
    fun getByBirthDate(date: LocalDate): Flow<List<PatientDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientDataSourceModel): Long

    @Update
    suspend fun update(patient: PatientDataSourceModel)

    @Delete
    suspend fun delete(patient: PatientDataSourceModel)

}