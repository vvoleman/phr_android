package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.ProductFormDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductFormDao {

    @Query("SELECT * FROM product_form")
    fun getAll(): Flow<List<ProductFormDataSourceModel>>

    @Query("SELECT * FROM product_form WHERE id = :id")
    fun getById(id: String): Flow<ProductFormDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productForm: ProductFormDataSourceModel)

    @Delete
    suspend fun delete(productForm: ProductFormDataSourceModel)
}
