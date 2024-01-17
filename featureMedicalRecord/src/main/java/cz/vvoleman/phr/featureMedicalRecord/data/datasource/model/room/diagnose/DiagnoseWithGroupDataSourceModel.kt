package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose

import androidx.room.Embedded
import androidx.room.Relation

data class DiagnoseWithGroupDataSourceModel(
    @Embedded val diagnose: DiagnoseDataSourceModel,
    @Relation(
        parentColumn = "parent",
        entityColumn = "id"
    )
    val diagnoseGroup: DiagnoseGroupDataSourceModel
)
