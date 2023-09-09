package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SubstanceAmountDao {

    @Query("SELECT * FROM substance_amount WHERE medicine_id = :medicineId")
    fun getAll(medicineId: String): Flow<List<SubstanceAmountDataSourceModel>>

    @Query("SELECT * FROM substance_amount WHERE id = :id")
    fun getById(id: String): Flow<SubstanceAmountDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(substanceAmount: SubstanceAmountDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(substanceAmounts: List<SubstanceAmountDataSourceModel>)

    @Delete
    suspend fun delete(substanceAmount: SubstanceAmountDataSourceModel)
}
