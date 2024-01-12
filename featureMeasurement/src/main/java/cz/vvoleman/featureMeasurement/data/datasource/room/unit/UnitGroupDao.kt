package cz.vvoleman.featureMeasurement.data.datasource.room.unit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitGroupDao {

    @Transaction
    @Query("SELECT * from unit_group")
    fun getAll(): Flow<List<UnitGroupDataSourceModel>>

    @Transaction
    @Query("SELECT * from unit_group WHERE id = :id")
    fun getById(id: Int): Flow<UnitGroupDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(unitGroup: UnitGroupDataSourceModel)

    @Delete
    suspend fun delete(unitGroup: UnitGroupDataSourceModel)

    @Query("DELETE FROM unit_group WHERE id = :id")
    suspend fun deleteById(id: Int)

}
