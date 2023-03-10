package cz.vvoleman.phr.data.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnoses")
    fun getAllDiagnoses(): Flow<List<DiagnoseWithGroup>>

    @Query("SELECT * FROM diagnoses WHERE id = :id")
    fun getDiagnoseById(id: String): Flow<DiagnoseWithGroup>

    @Query("SELECT * FROM diagnoses WHERE name LIKE '%'||:name||'%'")
    fun getDiagnosesByName(name: String): Flow<List<DiagnoseWithGroup>>

    @Query("SELECT * FROM diagnoses WHERE name LIKE '%'||:name||'%' LIMIT :limit OFFSET :offset")
    fun getDiagnoseWithGroupByName(name: String, limit: Int, offset: Int): Flow<List<DiagnoseWithGroup>>

    @Insert
    suspend fun insertDiagnose(diagnose: Diagnose)

    @Update
    suspend fun updateDiagnose(diagnose: Diagnose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertOrUpdateDiagnose(diagnoses: Diagnose)

    @Delete
    suspend fun deleteDiagnose(diagnose: Diagnose)

}