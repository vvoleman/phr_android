package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SubstanceDao {

    @Query("SELECT * FROM substance")
    fun getAll(): Flow<List<SubstanceDataSourceModel>>

    @Query("SELECT * FROM substance WHERE id = :id")
    fun getById(id: String): Flow<SubstanceDataSourceModel>

    @Query("SELECT * FROM substance WHERE name = :name")
    fun getByName(name: String): Flow<SubstanceDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(substance: SubstanceDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(substances: List<SubstanceDataSourceModel>)

    @Delete
    suspend fun delete(substance: SubstanceDataSourceModel)
}
