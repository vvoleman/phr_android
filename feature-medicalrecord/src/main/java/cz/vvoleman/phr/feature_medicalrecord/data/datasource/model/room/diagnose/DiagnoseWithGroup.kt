package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose

import androidx.room.Embedded
import androidx.room.Relation

data class DiagnoseWithGroup(
    @Embedded val diagnose: DiagnoseDataSourceModel,
    @Relation(
        parentColumn = "parent",
        entityColumn = "id"
    )
    val diagnoseGroup: DiagnoseGroupDataSourceModel
)
