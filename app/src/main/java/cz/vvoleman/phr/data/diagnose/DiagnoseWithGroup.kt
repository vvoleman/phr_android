package cz.vvoleman.phr.data.diagnose

import androidx.room.Embedded
import androidx.room.Relation

data class DiagnoseWithGroup(
    @Embedded val diagnose: Diagnose,
    @Relation(
        parentColumn = "parent",
        entityColumn = "id"
    )
    val diagnoseGroup: DiagnoseGroup
)
