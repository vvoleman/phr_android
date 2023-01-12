package cz.vvoleman.phr.data.diagnose

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnose")
    fun getAllDiagnoses(): List<Diagnose>

    @Query("SELECT * FROM diagnose WHERE id = :id")
    fun getDiagnoseById(id: Int): Diagnose

    @Query("SELECT * FROM diagnose WHERE name LIKE :name")
    fun getDiagnoseByName(name: String): Diagnose

    @Insert
    fun insertDiagnose(diagnose: Diagnose)

    @Update
    fun updateDiagnose(diagnose: Diagnose)

    @Delete
    fun deleteDiagnose(diagnose: Diagnose)

}