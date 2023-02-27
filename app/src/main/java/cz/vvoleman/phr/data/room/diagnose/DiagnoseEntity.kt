package cz.vvoleman.phr.data.room.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.Diagnose
import cz.vvoleman.phr.data.core.DiagnoseGroup

@Entity(tableName = "diagnose")
data class DiagnoseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val parent: String,
) {

    companion object {
        fun from(diagnose: Diagnose, group: DiagnoseGroup): DiagnoseEntity {
            return DiagnoseEntity(diagnose.id, diagnose.name, group.id)
        }
    }

    fun toDiagnose(): Diagnose {
        return Diagnose(id, name)
    }

}
