package cz.vvoleman.phr.data.room.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnose")
    fun getAll(): Flow<List<DiagnoseEntity>>

    @Query("SELECT * FROM diagnose WHERE id = :id")
    fun getById(id: String): Flow<DiagnoseWithGroup>

    @Query("SELECT * FROM diagnose WHERE parent = :id")
    fun getByParent(id: String): Flow<List<DiagnoseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnose: DiagnoseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnoses: List<DiagnoseEntity>)

    @Update
    suspend fun update(diagnose: DiagnoseEntity)

    @Delete
    suspend fun delete(diagnose: DiagnoseEntity)
}
