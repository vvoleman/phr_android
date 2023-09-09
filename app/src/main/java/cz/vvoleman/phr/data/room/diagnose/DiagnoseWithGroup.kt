package cz.vvoleman.phr.data.room.diagnose

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.core.diagnose.DiagnoseWithGroup as CoreDiagnoseWithGroup

data class DiagnoseWithGroup(
    @Embedded val diagnose: DiagnoseEntity,
    @Relation(
        parentColumn = "parent",
        entityColumn = "id"
    )
    val diagnoseGroup: DiagnoseGroupEntity
) {
    fun toDiagnoseWithGroup(): CoreDiagnoseWithGroup {
        return CoreDiagnoseWithGroup(diagnose.toDiagnose(), diagnoseGroup.toDiagnoseGroup())
    }
}
