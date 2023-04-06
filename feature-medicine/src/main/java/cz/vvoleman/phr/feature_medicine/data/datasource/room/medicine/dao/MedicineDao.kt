package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineWithDetails
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicine")
    fun getAll(): Flow<List<MedicineDataSourceModel>>

    @Query("SELECT * FROM medicine WHERE id IN (:medicineIds)")
    fun getManyById(medicineIds: List<String>): Flow<List<MedicineDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: MedicineDataSourceModel): Long

    @Delete
    suspend fun delete(medicine: MedicineDataSourceModel)

}