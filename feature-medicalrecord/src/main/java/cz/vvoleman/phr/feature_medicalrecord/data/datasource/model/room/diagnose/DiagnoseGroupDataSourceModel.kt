package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnose_group")
data class DiagnoseGroupDataSourceModel(
    @PrimaryKey val id: String,
    val name: String,
)
