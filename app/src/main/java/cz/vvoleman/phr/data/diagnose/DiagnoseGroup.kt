package cz.vvoleman.phr.data.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnose_groups")
data class DiagnoseGroup(
    @PrimaryKey val id: String,
    val name: String,
)