package cz.vvoleman.phr.common.data.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "patient")
data class PatientDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    @Embedded val address: AddressDataSourceModel? = null,
    @ColumnInfo(name = "birth_date") val birthDate: LocalDate? = null,
    val gender: String? = null
)
