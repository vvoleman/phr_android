package cz.vvoleman.phr.data.room.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.Medicine
import java.time.LocalDate

@Entity(tableName = "medicine")
data class MedicineEntity(
    @PrimaryKey val medicineId: String,
    val name: String,
    val dosage: String,
    val info: String,
    val expires_at: LocalDate?,
    val created_at: LocalDate?
) {

    companion object {
        fun from(medicine: Medicine): MedicineEntity {
            return MedicineEntity(
                medicineId = medicine.id,
                name = medicine.name,
                dosage = medicine.dosage,
                info = medicine.info,
                expires_at = medicine.expiresAt,
                created_at = medicine.createdAt
            )
        }
    }

    fun toMedicine(): Medicine {
        return Medicine(
            id = medicineId,
            name = name,
            dosage = dosage,
            info = info,
            expiresAt = expires_at,
            createdAt = created_at
        )
    }

}
