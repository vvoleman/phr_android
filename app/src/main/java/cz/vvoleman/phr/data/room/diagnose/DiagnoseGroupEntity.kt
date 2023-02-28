package cz.vvoleman.phr.data.room.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.diagnose.DiagnoseGroup

@Entity(tableName = "diagnose_group")
data class DiagnoseGroupEntity(
    @PrimaryKey val id: String,
    val name: String,
) {
    companion object {
        fun from(diagnoseGroup: DiagnoseGroup): DiagnoseGroupEntity {
            return DiagnoseGroupEntity(diagnoseGroup.id, diagnoseGroup.name)
        }
    }

    fun toDiagnoseGroup(): DiagnoseGroup {
        return DiagnoseGroup(id, name)
    }
}
