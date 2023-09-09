package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao

import androidx.room.*
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.PackagingDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PackagingDao {

    @Query("SELECT * FROM packaging WHERE medicine_id = :medicineId")
    fun getAll(medicineId: String): Flow<List<PackagingDataSourceModel>>

    @Query("SELECT * FROM packaging WHERE id = :id")
    fun getById(id: String): Flow<PackagingDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(packaging: PackagingDataSourceModel)

    @Delete
    suspend fun delete(packaging: PackagingDataSourceModel)
}
