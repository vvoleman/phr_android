package cz.vvoleman.phr.data.diagnose

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithDiagnoses(
    @Embedded val diagnoseGroup: DiagnoseGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "parent"
    )
    val diagnoses: List<Diagnose>
)
