package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnose")
    fun getAll(): Flow<List<DiagnoseWithGroup>>

    @Query("SELECT * FROM diagnose WHERE id = :id")
    fun getById(id: String): Flow<DiagnoseWithGroup>

    @Query("SELECT * FROM diagnose WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): Flow<List<DiagnoseDataSourceModel>>

    @Query("SELECT * FROM diagnose WHERE parent = :id")
    fun getByParent(id: String): Flow<List<DiagnoseWithGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnose: DiagnoseDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnoses: List<DiagnoseDataSourceModel>)

    @Update
    suspend fun update(diagnose: DiagnoseDataSourceModel)

    @Delete
    suspend fun delete(diagnose: DiagnoseDataSourceModel)

}