package cz.vvoleman.phr.data.room.medical_record.worker

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.MedicalWorker
import cz.vvoleman.phr.data.room.address.AddressEntity

@Entity(tableName = "medical_worker")
data class MedicalWorkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val email: String?,
    val phone: String?,
    @Embedded val address: AddressEntity?,
) {

    companion object {
        fun from(medicalWorker: MedicalWorker): MedicalWorkerEntity {
            return MedicalWorkerEntity(
                id = medicalWorker.id,
                name = medicalWorker.name,
                email = medicalWorker.email,
                phone = medicalWorker.phone,
                address = medicalWorker.address?.let {
                    AddressEntity.from(medicalWorker.address)
                }
            )
        }
    }

    fun toMedicalWorker(): MedicalWorker {
        return MedicalWorker(
            id = id,
            name = name,
            email = email,
            phone = phone,
            address = address?.toAddress()
        )
    }

}
