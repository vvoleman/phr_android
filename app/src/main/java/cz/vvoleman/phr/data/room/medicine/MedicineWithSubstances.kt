package cz.vvoleman.phr.data.room.medicine

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity

data class MedicineWithSubstances(
    @Embedded val medicine: MedicineEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val substances: List<SubstanceEntity>
)
