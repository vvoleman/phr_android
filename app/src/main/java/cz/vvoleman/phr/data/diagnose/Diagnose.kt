package cz.vvoleman.phr.data.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnoses")
data class Diagnose(
    @PrimaryKey val id: String,
    val name: String,
    val parent: String
)



