package cz.vvoleman.phr.featureMeasurement.data.datasource.room.field

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NumericFieldDao {

    @Transaction
    @Query("SELECT * FROM numeric_field WHERE measurement_group_id = :measurementGroupId")
    fun getByMeasurementGroup(measurementGroupId: Int): Flow<List<NumericFieldDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM numeric_field WHERE id = :id")
    fun getById(id: Int): Flow<NumericFieldDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(numericFieldType: NumericFieldDataSourceModel): Long

    @Query("DELETE FROM numeric_field WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM numeric_field WHERE measurement_group_id = :measurementGroupId")
    fun deleteByMeasurementGroup(measurementGroupId: Int)

}
