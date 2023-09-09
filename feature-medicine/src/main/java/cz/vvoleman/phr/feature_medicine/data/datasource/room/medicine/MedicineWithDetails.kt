package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Relation

data class MedicineWithDetails(
    @Embedded val medicine: MedicineDataSourceModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "medicine_id"
    )
    val substances: List<SubstanceAmountDataSourceModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "medicine_id"
    )
    val packagingDataSourceModel: PackagingDataSourceModel

)
