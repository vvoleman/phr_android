package cz.vvoleman.phr.data.room.medicine

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cz.vvoleman.phr.data.core.Medicine
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity

data class MedicineWithSubstances(
    @Embedded val medicine: MedicineEntity,

    @Relation(
        entity = SubstanceEntity::class,
        parentColumn = "medicineId",
        entityColumn = "substanceId",
        associateBy = Junction(MedicineSubstanceCrossRef::class,)
    )
    val substances: List<SubstanceEntity>
) {

    companion object {
        fun from(medicine: Medicine): MedicineWithSubstances {
            return MedicineWithSubstances(
                MedicineEntity.from(medicine),
                medicine.substances.map { SubstanceEntity.from(it) })
        }
    }

    fun toMedicine(): Medicine {
        return Medicine(
            medicine.medicineId,
            medicine.name,
            medicine.dosage,
            medicine.info,
            medicine.expires_at,
            medicine.created_at,
            substances.map { it.toSubstance() }
        )
    }

    fun toCrossRef(): List<MedicineSubstanceCrossRef> {
        return substances.map { MedicineSubstanceCrossRef(medicine.medicineId, it.substanceId) }
    }
}
