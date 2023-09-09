package cz.vvoleman.phr.data.room.patient

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.Gender
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.data.room.address.AddressEntity
import java.time.LocalDate
import java.util.*

@Entity(tableName = "patient")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    @Embedded val address: AddressEntity? = null,
    val birth_date: LocalDate? = null,
    val gender: Gender? = null
) {

    companion object {
        fun from(patient: Patient): PatientEntity {
            return PatientEntity(
                id = patient.id,
                name = patient.name,
                address = patient.address?.let { AddressEntity.from(it) },
                birth_date = patient.birthDate,
                gender = patient.gender

            )
        }
    }

    fun toPatient(): Patient {
        return Patient(
            id = id,
            name = name,
            address = address?.toAddress(),
            birthDate = birth_date,
            gender = gender
        )
    }
}
