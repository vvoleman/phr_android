package cz.vvoleman.phr.common.data.datasource.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "patient")
data class PatientDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    @Embedded val address: AddressDataSourceModel? = null,
    val birth_date: LocalDate? = null,
    val gender: String? = null
)
