package cz.vvoleman.phr.data.room.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseGroupDao {

    @Query("SELECT * FROM diagnose_group")
    fun getAll(): Flow<List<DiagnoseGroupEntity>>

    @Query("SELECT * FROM diagnose_group WHERE id = :id")
    fun getById(id: String): Flow<DiagnoseGroupEntity>

    @Query("SELECT * FROM diagnose_group WHERE name = :name")
    fun getByName(name: String): Flow<DiagnoseGroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnoseGroup: DiagnoseGroupEntity)

    @Update
    suspend fun update(diagnoseGroup: DiagnoseGroupEntity)

    @Delete
    suspend fun delete(diagnoseGroup: DiagnoseGroupEntity)
}
